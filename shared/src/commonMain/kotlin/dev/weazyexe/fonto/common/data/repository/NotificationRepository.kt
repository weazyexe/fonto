package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.NotificationDataSource
import dev.weazyexe.fonto.common.data.mapper.toDao
import dev.weazyexe.fonto.common.data.mapper.toNotification
import dev.weazyexe.fonto.common.model.notification.Notification
import dev.weazyexe.fonto.common.model.notification.NotificationMeta
import dev.weazyexe.fonto.common.model.notification.encode
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json

internal class NotificationRepository(
    private val notificationDataSource: NotificationDataSource,
    private val postRepository: PostRepository,
    private val json: Json
) {

    suspend fun getAll(): List<Notification> {
        val notificationDaos = notificationDataSource.getAll().first()
        return notificationDaos.map { it.toNotification(json) }
    }

    fun getById(id: Notification.Id): Notification? {
        val dao = notificationDataSource.getById(id) ?: return null
        return dao.toNotification(json)
    }

    fun insert(
        id: Notification.Id,
        type: Notification.Type,
        isRead: Boolean,
        createdAt: Instant,
        meta: NotificationMeta
    ) {
        notificationDataSource.insert(
            id = id,
            type = type,
            isRead = isRead,
            createdAt = createdAt,
            meta = meta.encode(json)
        )
    }

    fun update(notification: Notification) {
        notificationDataSource.update(notification.toDao(json))
    }
}