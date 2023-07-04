package dev.weazyexe.fonto.features.notifications

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.model.feed.Feed
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

    override fun toggleNotificationsEnabled(id: Feed.Id) {
        val oldFeed = state.feeds.firstOrNull { it.id == id } ?: return
        val newFeed = oldFeed.copy(areNotificationsEnabled = !oldFeed.areNotificationsEnabled)

        dependencies.updateFeed(newFeed)
            .onSuccess { setState { copy(feeds = feeds.update(newFeed)) } }
            .launchIn(scope)
    }

    private fun loadFeeds() {
        dependencies.getAllFeeds()
            .onEach { setState { copy(feeds = it) } }
            .launchIn(scope)
    }

    private fun AsyncResult<List<Feed>>.update(feed: Feed): AsyncResult<List<Feed>> =
        map { feeds ->
            feeds.map {
                if (it.id == feed.id) {
                    feed
                } else {
                    it
                }
            }
        }

    private fun AsyncResult<List<Feed>>.firstOrNull(predicate: (Feed) -> Boolean): Feed? {
        val feeds = this as? AsyncResult.Success ?: return null
        return feeds.data.firstOrNull { predicate(it) }
    }
}