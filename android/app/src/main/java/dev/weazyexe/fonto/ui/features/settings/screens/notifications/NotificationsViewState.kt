package dev.weazyexe.fonto.ui.features.settings.screens.notifications

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.ui.features.feed.components.feed.FeedViewState

@Immutable
data class NotificationsViewState(
    val feeds: AsyncResult<List<FeedViewState>>
)