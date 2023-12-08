package dev.weazyexe.fonto.features.feed

import dev.weazyexe.fonto.common.model.feed.Post

sealed interface FeedAction {

    data class OnRefresh(val isSwipeRefresh: Boolean) : FeedAction

    data object OnPaginate : FeedAction

    data class OnOpenPostClick(val id: Post.Id) : FeedAction

    data class OnSavePostClick(val id: Post.Id) : FeedAction

    data class OnPostBecomeVisible(val id: Post.Id) : FeedAction

    data class OnScrollChange(
        val firstVisibleItemIndex: Int,
        val firstVisibleItemOffset: Int
    ) : FeedAction

    data object OnScrollToTopClick : FeedAction
}
