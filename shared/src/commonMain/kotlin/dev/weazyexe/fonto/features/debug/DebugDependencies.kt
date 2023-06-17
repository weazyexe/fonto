package dev.weazyexe.fonto.features.debug

import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.data.usecase.feed.CreateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.DeleteAllFeedsUseCase

internal data class DebugDependencies(
    val initialState: DebugDomainState,
    val deleteAllFeeds: DeleteAllFeedsUseCase,
    val createFeed: CreateFeedUseCase,
    val eventBus: EventBus
)