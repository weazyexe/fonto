package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.PaginationState
import dev.weazyexe.fonto.ui.features.feed.viewstates.PostsViewState

@Immutable
data class FeedViewState(
    val posts: AsyncResult<PostsViewState> = AsyncResult.Loading(),
    val paginationState: PaginationState = PaginationState.IDLE,
    val isSwipeRefreshing: Boolean = false,
    val isSearchBarActive: Boolean = false
)
