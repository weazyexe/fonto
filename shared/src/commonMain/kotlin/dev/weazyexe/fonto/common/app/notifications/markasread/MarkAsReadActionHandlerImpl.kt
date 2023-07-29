package dev.weazyexe.fonto.common.app.notifications.markasread

import dev.weazyexe.fonto.common.data.usecase.notification.MarkNotificationAsReadUseCase
import dev.weazyexe.fonto.common.model.notification.Notification

internal class MarkAsReadActionHandlerImpl(
    private val markNotificationAsRead: MarkNotificationAsReadUseCase
) : MarkAsReadActionHandler {

    override fun handle(id: Notification.Id) {
        markNotificationAsRead(id)
    }
}