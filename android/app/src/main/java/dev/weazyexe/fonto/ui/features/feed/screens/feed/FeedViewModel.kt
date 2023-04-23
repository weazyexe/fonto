package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.DEFAULT_LIMIT
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetNewslineUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetPaginatedNewslineUseCase
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.ScrollState
import dev.weazyexe.fonto.core.ui.pagination.PaginationState
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.asViewState
import dev.weazyexe.fonto.core.ui.utils.asResponseError
import dev.weazyexe.fonto.data.SettingsStorage
import dev.weazyexe.fonto.domain.OpenPostPreference
import dev.weazyexe.fonto.ui.features.feed.viewstates.NewslineViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.PostViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.asNewslineViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.asViewState
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getFeed: GetFeedUseCase,
    private val getNewsline: GetNewslineUseCase,
    private val getPaginatedNewsline: GetPaginatedNewslineUseCase,
    private val settingsStorage: SettingsStorage
) : CoreViewModel<FeedState, FeedEffect>() {

    override val initialState: FeedState = FeedState()

    init {
        loadNewsline()
    }

    fun loadNewsline(isSwipeRefreshing: Boolean = false) = viewModelScope.launch {
        setState {
            copy(
                newslineLoadState = if (isSwipeRefreshing) {
                    newslineLoadState
                } else {
                    LoadState.Loading()
                },
                scrollState = ScrollState(),
                offset = 0,
                isSwipeRefreshing = isSwipeRefreshing
            )
        }

        val feeds = request { getFeed() }
            .withErrorHandling {
                setState { copy(newslineLoadState = LoadState.Error(it)) }
            } ?: return@launch
        setState { copy(feeds = feeds.data) }

        val newsline = request { getNewsline(feeds.data) }
            .withErrorHandling {
                setState { copy(newslineLoadState = LoadState.Error(it)) }
            } ?: return@launch

        if (newsline.data.loadedWithError.isNotEmpty() && newsline.data.loadedWithError.size == feeds.data.size) {
            val error = newsline.data.loadedWithError.first().throwable.asResponseError()
            setState {
                copy(
                    newslineLoadState = LoadState.Error(error),
                    isSwipeRefreshing = false
                )
            }
        } else {
            setState {
                copy(
                    newslineLoadState = newsline.asViewState { it.asNewslineViewState() },
                    offset = state.offset + DEFAULT_LIMIT,
                    isSwipeRefreshing = false
                )
            }
            showNotLoadedSourcesError(newsline.data.loadedWithError.map { it.feed })
        }
    }

    fun onScroll(state: ScrollState) {
        setState { copy(scrollState = state) }
    }

    fun getNextPostsBatch() = viewModelScope.launch {
        setState { copy(newslinePaginationState = PaginationState.LOADING) }

        val newslineBatch = request {
            getPaginatedNewsline(state.feeds, state.limit, state.offset)
        }
            .withErrorHandling {
                setState { copy(newslinePaginationState = PaginationState.ERROR) }
            } ?: return@launch

        val currentPosts =
            (state.newslineLoadState as? LoadState.Data)?.data?.posts ?: return@launch
        val newPosts = newslineBatch.data.posts.map { it.asViewState() }

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
                offset = state.offset + DEFAULT_LIMIT
            )
        }
    }

    fun openPost(post: PostViewState) = viewModelScope.launch {
        when (settingsStorage.getOpenPostPreference()) {
            OpenPostPreference.INTERNAL -> FeedEffect.OpenPostInApp(post.id, post.sourceId).emit()
            OpenPostPreference.DEFAULT_BROWSER -> FeedEffect.OpenPostInBrowser(post.link).emit()
        }
    }

    private fun showNotLoadedSourcesError(problematicFeedList: List<Feed>) {
        if (problematicFeedList.isNotEmpty()) {
            val feedListString = problematicFeedList.joinToString { it.title }
            FeedEffect.ShowMessage(R.string.feed_error_sources, feedListString).emit()
        }
    }
}
