package dev.weazyexe.fonto.ui.screens.feed.viewstates

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.domain.Feed

@Immutable
data class FeedViewState(
    val posts: List<PostViewState>
)

fun Feed.asViewState(): FeedViewState = FeedViewState(
    posts = posts.map { it.asViewState() }
)