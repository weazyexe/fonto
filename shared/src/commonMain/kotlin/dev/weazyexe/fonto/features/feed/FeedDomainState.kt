package dev.weazyexe.fonto.features.feed

import dev.weazyexe.fonto.arch.DomainState
import dev.weazyexe.fonto.common.DEFAULT_LIMIT
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.PaginationState
import dev.weazyexe.fonto.common.model.feed.Posts

data class FeedDomainState(
    val posts: AsyncResult<Posts> = AsyncResult.Loading(),
    val paginationState: PaginationState = PaginationState.IDLE,
    val isSwipeRefreshing: Boolean = false,
    val limit: Int = DEFAULT_LIMIT,
    val offset: Int = 0,
    val isSearchBarActive: Boolean = false,
    val firstVisibleItemIndex: Int = 0,
    val firstVisibleItemOffset: Int = 0
) : DomainState {

    val postsList: Posts?
        get() = (posts as? AsyncResult.Success)?.data
}