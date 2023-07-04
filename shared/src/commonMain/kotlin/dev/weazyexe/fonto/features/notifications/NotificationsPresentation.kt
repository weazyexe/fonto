package dev.weazyexe.fonto.features.notifications

import dev.weazyexe.fonto.arch.Presentation
import dev.weazyexe.fonto.common.model.feed.Feed

abstract class NotificationsPresentation :
    Presentation<NotificationsDomainState, NotificationsEffect>() {

    abstract fun toggleNotificationsEnabled(id: Feed.Id)
}