package dev.weazyexe.fonto.features.managefeed

import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.data.usecase.feed.DeleteFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetAllFeedsUseCase

internal data class ManageFeedDependencies(
    val initialState: ManageFeedDomainState,
    val getAllFeeds: GetAllFeedsUseCase,
    val deleteFeed: DeleteFeedUseCase,
    val eventBus: EventBus
)
