package dev.weazyexe.fonto.ui.features.feed.viewstates

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.model.feed.Newsline
import dev.weazyexe.fonto.ui.features.feed.components.post.PostViewState
import dev.weazyexe.fonto.ui.features.feed.components.post.asViewState

@Immutable
data class NewslineViewState(
    val posts: List<PostViewState> = emptyList()
)

fun Newsline.Success.asNewslineViewState(): NewslineViewState =
    NewslineViewState(posts = posts.map { it.asViewState() })