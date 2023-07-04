package dev.weazyexe.fonto.features.notifications

import dev.weazyexe.fonto.common.data.usecase.feed.GetAllFeedsUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.UpdateFeedUseCase

internal data class NotificationsDependencies(
    val initialState: NotificationsDomainState,
    val getAllFeeds: GetAllFeedsUseCase,
    val updateFeed: UpdateFeedUseCase
)