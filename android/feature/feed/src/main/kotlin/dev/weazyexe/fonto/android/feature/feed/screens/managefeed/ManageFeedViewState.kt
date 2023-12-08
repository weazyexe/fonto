package dev.weazyexe.fonto.android.feature.feed.screens.managefeed

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.android.feature.feed.components.feed.FeedViewState
import dev.weazyexe.fonto.common.data.AsyncResult

@Immutable
data class ManageFeedViewState(
    val feeds: AsyncResult<List<FeedViewState>> = AsyncResult.Loading(),
    val hasChanges: Boolean = false
)
