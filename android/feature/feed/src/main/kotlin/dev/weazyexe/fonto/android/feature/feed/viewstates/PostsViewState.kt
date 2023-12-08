package dev.weazyexe.fonto.android.feature.feed.viewstates

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.android.feature.feed.components.post.PostViewState
import dev.weazyexe.fonto.android.feature.feed.components.post.asViewState
import dev.weazyexe.fonto.common.model.feed.Posts

@Immutable
data class PostsViewState(
    val posts: List<PostViewState> = emptyList()
): List<PostViewState> by posts

fun Posts.asViewState(): PostsViewState =
    PostsViewState(posts = posts.map { it.asViewState() })
