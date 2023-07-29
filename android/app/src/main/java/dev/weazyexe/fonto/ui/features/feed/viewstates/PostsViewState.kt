package dev.weazyexe.fonto.ui.features.feed.viewstates

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.ui.features.feed.components.post.PostViewState
import dev.weazyexe.fonto.ui.features.feed.components.post.asViewState

@Immutable
data class PostsViewState(
    val posts: List<PostViewState> = emptyList()
) : List<PostViewState> by posts

fun List<Post>.asViewState(): PostsViewState =
    PostsViewState(posts = map { it.asViewState() })