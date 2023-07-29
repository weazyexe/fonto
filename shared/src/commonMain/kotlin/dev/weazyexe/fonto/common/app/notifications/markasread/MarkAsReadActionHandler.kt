package dev.weazyexe.fonto.common.app.notifications.markasread

import dev.weazyexe.fonto.common.model.notification.Notification

interface MarkAsReadActionHandler {

    fun handle(id: Notification.Id)
}