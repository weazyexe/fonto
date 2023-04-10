package dev.weazyexe.fonto.ui.screens.feed.managefeed.viewstates

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.model.feed.Feed

@Immutable
data class ManageFeedViewState(
    val feeds: List<Feed>
)