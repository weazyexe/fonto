package dev.weazyexe.fonto.common.data.mapper

import dev.weazyexe.fonto.common.db.NotificationDao
import dev.weazyexe.fonto.common.model.notification.Notification
import dev.weazyexe.fonto.common.model.notification.NotificationMeta
import dev.weazyexe.fonto.common.model.notification.decode
import dev.weazyexe.fonto.common.model.notification.encode
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json

internal fun Notification.toDao(json: Json): NotificationDao =
    NotificationDao(
        id = id.origin,
        type = type.origin,
        isRead = isRead.toString(),
        createdAt = createdAt.epochSeconds,
        meta = meta.encode(json)
    )

internal fun NotificationDao.toNotification(json: Json): Notification {
    val type = Notification.Type.valueOf(type)

    return Notification(
        id = Notification.Id(id),
        type = type,
        isRead = isRead.toBooleanStrict(),
        createdAt = Instant.fromEpochSeconds(createdAt),
        meta = when (type) {
            Notification.Type.NEW_POSTS -> meta.decode<NotificationMeta.NewPosts>(json)
        }
    )
}