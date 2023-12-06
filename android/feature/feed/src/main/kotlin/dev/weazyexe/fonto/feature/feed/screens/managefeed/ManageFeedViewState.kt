package dev.weazyexe.fonto.feature.feed.screens.managefeed

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.feature.feed.components.feed.FeedViewState

@Immutable
data class ManageFeedViewState(
    val feeds: AsyncResult<List<FeedViewState>> = AsyncResult.Loading(),
    val hasChanges: Boolean = false
)
