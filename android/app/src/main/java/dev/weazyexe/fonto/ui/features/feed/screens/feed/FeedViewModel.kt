package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.DEFAULT_LIMIT
import dev.weazyexe.fonto.common.data.bus.AppEvent
import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.data.usecase.feed.GetAllFeedsUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetNewslineUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetPaginatedNewslineUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.UpdatePostUseCase
import dev.weazyexe.fonto.common.feature.newsline.ByFeed
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.feature.settings.SettingsStorage
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Newsline
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.ScrollState
import dev.weazyexe.fonto.core.ui.pagination.PaginationState
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.ResponseError
import dev.weazyexe.fonto.core.ui.presentation.asViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.NewslineViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.PostViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.asNewslineViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.asPost
import dev.weazyexe.fonto.ui.features.feed.viewstates.asViewState
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getAllFeeds: GetAllFeedsUseCase,
    private val getNewsline: GetNewslineUseCase,
    private val updatePost: UpdatePostUseCase,
    private val getPaginatedNewsline: GetPaginatedNewslineUseCase,
    private val settingsStorage: SettingsStorage,
    private val eventBus: EventBus
) : CoreViewModel<FeedState, FeedEffect>() {

    override val initialState: FeedState = FeedState()

    init {
        loadNewsline()
        listenToEventBus()
    }

    fun loadNewsline(
        isSwipeRefreshing: Boolean = false,
        useCache: Boolean = false
    ) = viewModelScope.launch {
        setState {
            copy(
                newslineLoadState = if (isSwipeRefreshing) {
                    newslineLoadState
                } else {
                    LoadState.Loading()
                },
                scrollState = ScrollState(),
                offset = 0,
                isSwipeRefreshing = isSwipeRefreshing,
                newslinePaginationState = PaginationState.IDLE
            )
        }

        val newsline = request { getNewsline(state.filters, useCache) }
            .withErrorHandling {
                setState { copy(newslineLoadState = LoadState.Error(it)) }
            } ?: return@launch

        when (val data = newsline.data) {
            is Newsline.Success -> {
                setState {
                    copy(
                        newslineLoadState = LoadState.Data(data.asNewslineViewState()),
                        offset = state.offset + DEFAULT_LIMIT,
                        isSwipeRefreshing = false,
                        filters = data.filters
                    )
                }
                showNotLoadedSourcesError(data.loadedWithError.map { it.feed })
            }

            is Newsline.Error -> {
                setState {
                    copy(
                        newslineLoadState = LoadState.Error(ResponseError.FetchNewslineError()),
                        isSwipeRefreshing = false
                    )
                }
            }
        }
    }

    fun getNextPostsBatch() = viewModelScope.launch {
        setState { copy(newslinePaginationState = PaginationState.LOADING) }

        val newslineBatch = request {
            getPaginatedNewsline(state.limit, state.offset, state.filters)
        }.withErrorHandling {
            setState { copy(newslinePaginationState = PaginationState.ERROR) }
        } ?: return@launch

        when (val data = newslineBatch.data) {
            is Newsline.Success -> {
                val currentPosts =
                    (state.newslineLoadState as? LoadState.Data)?.data?.posts ?: return@launch
                val newPosts = data.posts.map { it.asViewState() }

                val updatedNewsline = LoadState.Data(currentPosts + newPosts)
                    .asViewState { NewslineViewState(it) }

                setState {
                    copy(
                        newslineLoadState = updatedNewsline,
                        newslinePaginationState = if (newPosts.isNotEmpty()) {
                            PaginationState.IDLE
                        } else {
                            PaginationState.PAGINATION_EXHAUST
                        },
                        offset = state.offset + DEFAULT_LIMIT,
                        filters = data.filters
                    )
                }
            }
            is Newsline.Error -> {
                setState { copy(newslinePaginationState = PaginationState.ERROR) }
            }
        }
    }

    fun applyFilters(updatedFilter: NewslineFilter) {
        val newFilters = state.filters?.map {
            when (it.javaClass) {
                updatedFilter.javaClass -> updatedFilter
                else -> it
            }
        }
        setState { copy(filters = newFilters) }
        loadNewsline(useCache = true)
    }

    fun openMultipleValuePicker(updatedFilter: NewslineFilter) {
        when (updatedFilter) {
            is ByFeed -> FeedEffect.OpenFeedPicker(
                values = updatedFilter.values.findFeeds(),
                possibleValues = updatedFilter.possibleValues.findFeeds(),
                title = R.string.feed_filters_sources
            ).emit()

            else -> {
                // Do nothing
            }
        }
    }

    fun savePost(post: PostViewState) = viewModelScope.launch {
        val updatedPost = post.copy(isSaved = !post.isSaved)
        request { updatePost(post = updatedPost.asPost()) }
            .withErrorHandling {
                FeedEffect.ShowMessage(
                    message = if (updatedPost.isSaved) {
                        R.string.feed_post_saving_error
                    } else {
                        R.string.feed_post_removing_from_bookmarks_error
                    }
                ).emit()
            } ?: return@launch

        val loadState = state.newslineLoadState as? LoadState.Data ?: return@launch
        val updatedPosts = loadState.data.posts.map {
            if (it.id == post.id) {
                updatedPost
            } else {
                it
            }
        }

        setState {
            copy(
                newslineLoadState = loadState.copy(
                    data = loadState.data.copy(
                        posts = updatedPosts
                    )
                )
            )
        }

        FeedEffect.ShowMessage(
            message = if (updatedPost.isSaved) {
                R.string.feed_post_saved_to_bookmarks
            } else {
                R.string.feed_post_removed_from_bookmarks
            }
        ).emit()
    }

    fun openPost(post: PostViewState) = viewModelScope.launch {
        if (!state.isBenchmarking) {
            when (settingsStorage.getOpenPostPreference()) {
                OpenPostPreference.INTERNAL -> FeedEffect.OpenPostInApp(
                    post.link,
                    settingsStorage.getTheme()
                ).emit()

                OpenPostPreference.DEFAULT_BROWSER -> FeedEffect.OpenPostInBrowser(post.link).emit()
            }
        }
    }

    fun onScroll(state: ScrollState) {
        setState { copy(scrollState = state) }
    }

    fun getFeedTitleById(id: Feed.Id): String {
        return id.findFeed().title
    }

    private suspend fun loadFeeds(): List<Feed> {
        val feeds = request { getAllFeeds() }
            .withErrorHandling {
                setState { copy(newslineLoadState = LoadState.Error(it)) }
            } ?: return emptyList()

        return feeds.data
    }

    private fun showNotLoadedSourcesError(problematicFeedList: List<Feed>) {
        if (problematicFeedList.isNotEmpty()) {
            val feedListString = problematicFeedList.joinToString { it.title }
            FeedEffect.ShowMessage(R.string.feed_error_sources, feedListString).emit()
        }
    }

    private fun listenToEventBus() {
        eventBus.observe()
            .filter { it is AppEvent.RefreshFeed }
            .onEach { loadNewsline() }
            .launchInViewModelScope()
    }

    private fun List<Feed.Id>.findFeeds(): List<Feed> =
        map { id -> id.findFeed() }

    private fun Feed.Id.findFeed(): Feed =
        state.feeds.first { it.id == this }
}
