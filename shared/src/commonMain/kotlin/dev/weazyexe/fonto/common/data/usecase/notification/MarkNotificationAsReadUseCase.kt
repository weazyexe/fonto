package dev.weazyexe.fonto.common.data.usecase.notification

import dev.weazyexe.fonto.common.data.repository.NotificationRepository
import dev.weazyexe.fonto.common.model.notification.Notification

internal class MarkNotificationAsReadUseCase(
    private val notificationRepository: NotificationRepository,
    private val updateNotification: UpdateNotificationUseCase
) {

    operator fun invoke(id: Notification.Id) {
        val notification = notificationRepository.getById(id) ?: return
        updateNotification(notification = notification.copy(isRead = true))
    }
}