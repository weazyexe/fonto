package dev.weazyexe.fonto.common.app.notifications

import dev.weazyexe.fonto.common.model.notification.NotificationVisuals

interface Notifier {

    fun notify(visuals: NotificationVisuals)
}