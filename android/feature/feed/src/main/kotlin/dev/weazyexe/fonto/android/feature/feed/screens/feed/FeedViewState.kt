package dev.weazyexe.fonto.android.feature.feed.screens.feed

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.android.feature.feed.viewstates.PostsViewState
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.PaginationState

@Immutable
data class FeedViewState(
    val posts: AsyncResult<PostsViewState> = AsyncResult.Loading(),
    val paginationState: PaginationState = PaginationState.IDLE,
    val isSwipeRefreshing: Boolean = false,
    val isSearchBarActive: Boolean = false,
    val firstVisibleItemIndex: Int = 0,
    val firstVisibleItemOffset: Int = 0
)
