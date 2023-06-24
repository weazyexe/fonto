package dev.weazyexe.fonto.features.debug

import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.data.usecase.feed.CreateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.DeleteAllFeedsUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.DeleteAllPostsUseCase

internal data class DebugDependencies(
    val initialState: DebugDomainState,
    val deleteAllFeeds: DeleteAllFeedsUseCase,
    val createFeed: CreateFeedUseCase,
    val deleteAllPosts: DeleteAllPostsUseCase,
    val eventBus: EventBus
)