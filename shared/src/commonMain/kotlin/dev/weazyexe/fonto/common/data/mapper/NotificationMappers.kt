package dev.weazyexe.fonto.common.data.mapper

import dev.weazyexe.fonto.common.db.NotificationDao
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.model.notification.Notification
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

internal fun Notification.toDao(json: Json): NotificationDao =
    NotificationDao(
        id = id.origin,
        newPostIdentifiers = newPosts.map { it.id }.toJson(json),
        isRead = isRead.toString()
    )

internal fun NotificationDao.toNotification(newPosts: List<Post>): Notification =
    Notification(
        id = Notification.Id(id),
        newPosts = newPosts,
        isRead = isRead.toBooleanStrict()
    )

internal fun List<Post.Id>.toJson(json: Json): String {
    return json.encodeToString(
        serializer = ListSerializer(Post.Id.serializer()),
        value = this
    )
}

internal fun String.toPostIdList(json: Json): List<Post.Id> {
    return json.decodeFromString(
        deserializer = ListSerializer(Post.Id.serializer()),
        string = this
    )
}