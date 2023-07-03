package dev.weazyexe.fonto.features.notifications

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class NotificationsPresentationImpl(
    private val dependencies: NotificationsDependencies
) : NotificationsPresentation() {

    override val initialState: NotificationsDomainState
        get() = dependencies.initialState

    override fun onCreate(scope: CoroutineScope) {
        super.onCreate(scope)
        loadFeeds()
    }

    private fun loadFeeds() {
        dependencies.getAllFeeds()
            .onEach { setState { copy(feeds = it) } }
            .launchIn(scope)
    }
}