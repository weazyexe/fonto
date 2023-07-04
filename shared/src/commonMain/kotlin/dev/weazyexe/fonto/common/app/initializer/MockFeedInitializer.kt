package dev.weazyexe.fonto.common.app.initializer

import dev.weazyexe.fonto.common.data.usecase.feed.CreateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.DeleteAllFeedsUseCase
import dev.weazyexe.fonto.common.feature.debug.VALID_FEED
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge

internal class MockFeedInitializer(
    private val deleteAllFeeds: DeleteAllFeedsUseCase,
    private val createFeed: CreateFeedUseCase
) : Initializer<MockFeedInitializer.Args> {

    override suspend fun initialize(arguments: Args) {
        if (arguments.areMockFeedsEnabled) {
            deleteAllFeeds()
                .flatMapLatest { VALID_FEED.asFlow() }
                .flatMapMerge {
                    createFeed(
                        title = it.title,
                        link = it.link,
                        image = it.icon,
                        type = it.type,
                        category = it.category,
                        areNotificationsEnabled = it.areNotificationsEnabled
                    )
                }
                .collect()
        }
    }

    @JvmInline
    value class Args(val areMockFeedsEnabled: Boolean)
}