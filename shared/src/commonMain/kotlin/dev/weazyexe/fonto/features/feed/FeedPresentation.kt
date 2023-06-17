package dev.weazyexe.fonto.features.feed

import dev.weazyexe.fonto.arch.Presentation
import dev.weazyexe.fonto.common.model.feed.Post

abstract class FeedPresentation : Presentation<FeedDomainState, FeedEffect>() {

    abstract fun onSearchBarActiveChange(isActive: Boolean)

    abstract fun loadPosts(isSwipeRefreshing: Boolean)

    abstract fun loadMorePosts()

    abstract fun openPost(id: Post.Id)

    abstract fun savePost(id: Post.Id)
}