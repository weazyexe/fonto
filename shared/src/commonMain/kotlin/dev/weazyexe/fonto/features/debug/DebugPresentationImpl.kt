package dev.weazyexe.fonto.features.debug

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.bus.AppEvent
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.feature.debug.FULLY_INVALID_FEED
import dev.weazyexe.fonto.common.feature.debug.PARTIALLY_INVALID_FEED
import dev.weazyexe.fonto.common.feature.debug.VALID_FEED
import dev.weazyexe.fonto.common.model.feed.Feed
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

internal class DebugPresentationImpl(
    private val dependencies: DebugDependencies
) : DebugPresentation() {

    override val initialState: DebugDomainState
        get() = dependencies.initialState

    override fun addMockFeeds() {
        addMockFeeds(VALID_FEED)
    }

    override fun addPartialInvalidMockFeeds() {
        addMockFeeds(PARTIALLY_INVALID_FEED)
    }

    override fun addInvalidMockFeeds() {
        addMockFeeds(FULLY_INVALID_FEED)
    }

    private fun addMockFeeds(feed: List<Feed>) = scope.launch {
        dependencies.deleteAllFeeds()
            .filterIsInstance<AsyncResult.Success<*>>()
            .flatMapLatest { feed.asFlow() }
            .flatMapMerge { dependencies.createFeed(it.title, it.link, it.icon, it.type, it.category) }
            .filterIsInstance<AsyncResult.Success<*>>()
            .drop(feed.size - 1)
            .onSuccess {
                DebugEffect.ShowFeedsAddedSuccessfullyMessage.emit()
                dependencies.eventBus.emit(AppEvent.RefreshFeed)
            }
            .launchIn(scope)
    }
}