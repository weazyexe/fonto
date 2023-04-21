package dev.weazyexe.fonto.ui.features.feed.screens.post

import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post

data class PostScreenArgs(
    val postId: Post.Id,
    val feedId: Feed.Id
)
