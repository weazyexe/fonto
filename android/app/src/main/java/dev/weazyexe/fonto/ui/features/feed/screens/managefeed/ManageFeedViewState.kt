package dev.weazyexe.fonto.ui.features.feed.screens.managefeed

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.ui.features.feed.components.feed.FeedViewState

@Immutable
data class ManageFeedViewState(
    val feeds: AsyncResult<List<FeedViewState>> = AsyncResult.Loading(),
    val hasChanges: Boolean = false
)
