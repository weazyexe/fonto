package dev.weazyexe.fonto.features.notifications

import dev.weazyexe.fonto.arch.Presentation

abstract class NotificationsPresentation :
    Presentation<NotificationsDomainState, NotificationsEffect>()