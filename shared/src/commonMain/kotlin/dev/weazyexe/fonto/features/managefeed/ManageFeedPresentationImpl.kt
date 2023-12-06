package dev.weazyexe.fonto.features.managefeed

import dev.weazyexe.fonto.common.data.bus.AppEvent
import dev.weazyexe.fonto.common.data.onError
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.model.feed.Feed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class ManageFeedPresentationImpl(
    private val dependencies: ManageFeedDependencies
) : ManageFeedPresentation() {

    override val initialState: ManageFeedDomainState
        get() = dependencies.initialState

    override fun onCreate(scope: CoroutineScope) {
        super.onCreate(scope)
        loadFeed()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (state.hasChanges) {
            dependencies.eventBus.emit(AppEvent.RefreshFeed)
        }
    }

    override fun loadFeed() {
        dependencies.getAllFeeds()
            .onEach { setState { copy(feeds = it) } }
            .launchIn(scope)
    }

    override fun deleteFeedById(id: Feed.Id) {
        dependencies.deleteFeed(id)
            .onError { ManageFeedEffect.ShowDeletionFailedMessage.emit() }
            .onSuccess {
                ManageFeedEffect.ShowDeletedSuccessfullyMessage.emit()
                updateChangesStatus()
                loadFeed()
            }
            .launchIn(scope)
    }

    override fun updateChangesStatus() {
        setState { copy(hasChanges = true) }
    }
}
