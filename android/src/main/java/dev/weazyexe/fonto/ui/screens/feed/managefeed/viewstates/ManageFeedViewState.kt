package dev.weazyexe.fonto.ui.screens.feed.managefeed.viewstates

import androidx.compose.runtime.Immutable

@Immutable
data class ManageFeedViewState(
    val feeds: List<FeedViewState>
)