package dev.weazyexe.fonto.common.data.usecase.notification

import dev.weazyexe.fonto.common.data.repository.NotificationRepository
import dev.weazyexe.fonto.common.model.notification.Notification

internal class UpdateNotificationUseCase(
    private val notificationRepository: NotificationRepository
) {

    operator fun invoke(notification: Notification) {
        notificationRepository.update(notification)
    }
}