package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.features.feed.FeedDependencies
import dev.weazyexe.fonto.features.feed.FeedDomainState
import dev.weazyexe.fonto.features.feed.FeedPresentation
import dev.weazyexe.fonto.ui.features.feed.viewstates.asViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FeedViewModel(dependencies: FeedDependencies) : ViewModel() {

    private val presentation = FeedPresentation(
        scope = viewModelScope,
        dependencies = dependencies
    )

    val state: Flow<FeedViewState>
        get() = presentation.domainState.map { it.asViewState()  }

    init {
        presentation.onCreate()
    }

    fun loadMorePosts() {
        presentation.loadMorePosts()
    }

    private fun FeedDomainState.asViewState(): FeedViewState =
        FeedViewState(
            posts = posts.map { it.asViewState() },
            paginationState = paginationState,
            isSwipeRefreshing = isSwipeRefreshing,
            isSearchBarActive = isSearchBarActive
        )
    /*

    fun getNextPostsBatch() = viewModelScope.launch {
        setState { copy(newslinePaginationState = PaginationState.LOADING) }

        val newslineBatch = request {
            getPaginatedNewsline(state.limit, state.offset)
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
                        offset = state.offset + DEFAULT_LIMIT
                    )
                }
            }

            is Newsline.Error -> {
                setState { copy(newslinePaginationState = PaginationState.ERROR) }
            }
        }
    }

    fun savePost(post: PostViewState) = viewModelScope.launch {
        val updatedPost = post.copy(isSaved = !post.isSaved)
        request { updatePost(post = updatedPost.asPost()) }
            .withErrorHandling {
                FeedEffect.ShowMessage(
                    message = if (updatedPost.isSaved) {
                        StringResources.feed_post_saving_error
                    } else {
                        StringResources.feed_post_removing_from_bookmarks_error
                    }
                ).emit()
            } ?: return@launch

        val loadState = state.newslineLoadState as? LoadState.Data ?: return@launch
        setState { copy(newslineLoadState = loadState.update(updatedPost)) }

        FeedEffect.ShowMessage(
            message = if (updatedPost.isSaved) {
                StringResources.feed_post_saved_to_bookmarks
            } else {
                StringResources.feed_post_removed_from_bookmarks
            }
        ).emit()
    }

    fun openPost(post: PostViewState) = viewModelScope.launch {
        if (!state.isBenchmarking) {
            when (settingsStorage.getOpenPostPreference()) {
                OpenPostPreference.INTERNAL -> FeedEffect.OpenPostInApp(
                    link = post.link,
                    theme = settingsStorage.getTheme()
                )

                OpenPostPreference.DEFAULT_BROWSER -> FeedEffect.OpenPostInBrowser(post.link)
            }.emit()

            val updatedPost = post.copy(isRead = true)
            request { updatePost(post = updatedPost.asPost()) }
                .withErrorHandling {  }?.data ?: return@launch

            setState { copy(newslineLoadState = state.newslineLoadState.update(updatedPost)) }
        }
    }

    fun onScroll(state: ScrollState) {
        setState { copy(scrollState = state) }
    }

    fun onSearchBarActiveChange(isActive: Boolean) {
        setState { copy(isSearchBarActive = isActive) }
    }

    private fun showNotLoadedSourcesError(problematicFeedList: List<Feed>) {
        if (problematicFeedList.isNotEmpty()) {
            val feedListString = problematicFeedList.joinToString { it.title }
            FeedEffect.ShowMessage(StringResources.feed_error_sources, feedListString).emit()
        }
    }

    private fun listenToEventBus() {
        eventBus.observe()
            .filter { it is AppEvent.RefreshFeed }
            .onEach { loadNewsline() }
            .launchInViewModelScope()
    }

    private fun LoadState<NewslineViewState>.update(post: PostViewState): LoadState<NewslineViewState> {
        val data = (this as? LoadState.Data)?.data ?: return this
        val updatedPosts = data.posts.updatePost(post)
        return update(updatedPosts)
    }

    private fun LoadState.Data<NewslineViewState>.update(posts: List<PostViewState>): LoadState.Data<NewslineViewState> {
        return copy(data = data.copy(posts = posts))
    }

    private fun List<PostViewState>.updatePost(post: PostViewState): List<PostViewState> {
        return map {
            if (it.id == post.id) {
                post
            } else {
                it
            }
        }
    }*/
}
