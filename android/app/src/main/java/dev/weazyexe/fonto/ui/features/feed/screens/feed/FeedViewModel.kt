package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.common.model.feed.Post
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

    val effects = presentation.effects

    init {
        presentation.onCreate()
    }

    fun onSearchBarActiveChange(isActive: Boolean) {
        presentation.onSearchBarActiveChange(isActive)
    }

    fun loadMorePosts() {
        presentation.loadMorePosts()
    }

    fun openPost(id: Post.Id) {
        presentation.openPost(id)
    }

    fun savePost(id: Post.Id) {
        presentation.savePost(id)
    }

    private fun FeedDomainState.asViewState(): FeedViewState =
        FeedViewState(
            posts = posts.map { it.asViewState() },
            paginationState = paginationState,
            isSwipeRefreshing = isSwipeRefreshing,
            isSearchBarActive = isSearchBarActive
        )
    /*

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
