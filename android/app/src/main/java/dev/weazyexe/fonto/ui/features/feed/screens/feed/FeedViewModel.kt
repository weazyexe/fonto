package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.features.feed.FeedDomainState
import dev.weazyexe.fonto.features.feed.FeedPresentation
import dev.weazyexe.fonto.ui.features.feed.viewstates.asViewState
import dev.weazyexe.fonto.util.flow.mapState
import kotlinx.coroutines.flow.StateFlow

class FeedViewModel(private val presentation: FeedPresentation) : ViewModel() {

    val state: StateFlow<FeedViewState>
        get() = presentation.domainState.mapState { it.asViewState() }

    val effects = presentation.effects

    init {
        presentation.onCreate(viewModelScope)
    }

    fun onSearchBarActiveChange(isActive: Boolean) {
        presentation.onSearchBarActiveChange(isActive)
    }

    fun loadPosts(isSwipeRefreshing: Boolean) {
        presentation.loadPosts(isSwipeRefreshing)
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

    fun loadMetadataIfNeeds(id: Post.Id) {
        presentation.loadPostMetadataIfNeeds(id)
    }

    fun onScroll(firstVisibleItemIndex: Int, firstVisibleItemOffset: Int) {
        presentation.onScroll(firstVisibleItemIndex, firstVisibleItemOffset)
    }

    fun scrollToTop() {
        presentation.scrollToTop()
    }

    private fun FeedDomainState.asViewState(): FeedViewState =
        FeedViewState(
            posts = posts.map { it.asViewState() },
            paginationState = paginationState,
            isSwipeRefreshing = isSwipeRefreshing,
            isSearchBarActive = isSearchBarActive,
            firstVisibleItemIndex = firstVisibleItemIndex,
            firstVisibleItemOffset = firstVisibleItemOffset,
        )
}
