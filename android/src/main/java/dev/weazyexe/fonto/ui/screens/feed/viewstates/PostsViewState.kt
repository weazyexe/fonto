package dev.weazyexe.fonto.ui.screens.feed.viewstates

import androidx.compose.runtime.Immutable

@Immutable
data class PostsViewState(
    val posts: List<PostViewState>
)