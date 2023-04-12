package dev.weazyexe.fonto.ui.features.feed.viewstates

import androidx.compose.runtime.Immutable

@Immutable
data class PostsViewState(
    val posts: List<PostViewState>
)