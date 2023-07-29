package dev.weazyexe.fonto.common.data.usecase.notification

import dev.weazyexe.fonto.common.data.repository.NotificationRepository
import dev.weazyexe.fonto.common.model.notification.Notification

internal class GetLastNotificationUseCase(
    private val notificationRepository: NotificationRepository
) {

    suspend operator fun invoke(): Notification? {
        return notificationRepository.getAll().firstOrNull { !it.isRead }
    }
}