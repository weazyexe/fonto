package dev.weazyexe.fonto.ui.features.feed.viewstates

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.model.feed.Newsline

@Immutable
data class NewslineViewState(
    val posts: List<PostViewState>
)

fun Newsline.asNewslineViewState(): NewslineViewState =
    NewslineViewState(posts = posts.map { it.asViewState() })