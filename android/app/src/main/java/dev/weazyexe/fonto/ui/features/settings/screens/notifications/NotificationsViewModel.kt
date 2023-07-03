package dev.weazyexe.fonto.ui.features.settings.screens.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.features.notifications.NotificationsDomainState
import dev.weazyexe.fonto.features.notifications.NotificationsPresentation
import dev.weazyexe.fonto.ui.features.feed.components.feed.asViewState
import dev.weazyexe.fonto.util.flow.mapState

class NotificationsViewModel(
    private val presentation: NotificationsPresentation
) : ViewModel() {

    val state = presentation.domainState.mapState { it.asViewState() }
    val effects = presentation.effects

    init {
        presentation.onCreate(viewModelScope)
    }

    private fun NotificationsDomainState.asViewState() : NotificationsViewState =
        NotificationsViewState(
            feeds = feeds.map { feeds -> feeds.map { it.asViewState() } }
        )
}