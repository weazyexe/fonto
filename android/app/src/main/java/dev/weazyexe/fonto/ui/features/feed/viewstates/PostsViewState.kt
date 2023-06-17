package dev.weazyexe.fonto.ui.features.feed.viewstates

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.model.feed.Posts
import dev.weazyexe.fonto.ui.features.feed.components.post.PostViewState
import dev.weazyexe.fonto.ui.features.feed.components.post.asViewState

@Immutable
data class PostsViewState(
    val posts: List<PostViewState> = emptyList()
): List<PostViewState> by posts

fun Posts.asViewState(): PostsViewState =
    PostsViewState(posts = posts.map { it.asViewState() })