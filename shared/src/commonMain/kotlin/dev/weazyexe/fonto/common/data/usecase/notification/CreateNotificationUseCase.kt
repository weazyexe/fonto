package dev.weazyexe.fonto.common.data.usecase.notification

import dev.weazyexe.fonto.common.data.repository.NotificationRepository
import dev.weazyexe.fonto.common.model.notification.Notification
import dev.weazyexe.fonto.common.model.notification.NotificationMeta
import dev.weazyexe.fonto.utils.extensions.generateUUID
import kotlinx.datetime.Clock

internal class CreateNotificationUseCase(
    private val notificationRepository: NotificationRepository
) {

    operator fun invoke(meta: NotificationMeta): Notification {
        val id = generateNotificationId()
        val type = when (meta) {
            is NotificationMeta.NewPosts -> Notification.Type.NEW_POSTS
        }

        notificationRepository.insert(
            id = id,
            type = type,
            isRead = false,
            createdAt = Clock.System.now(),
            meta = meta
        )
        return notificationRepository.getById(id)
            ?: throw IllegalStateException("Failed to create notification $id")
    }

    private fun generateNotificationId(): Notification.Id =
        Notification.Id(origin = generateUUID())
}