package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.NotificationDataSource
import dev.weazyexe.fonto.common.data.mapper.toDao
import dev.weazyexe.fonto.common.data.mapper.toJson
import dev.weazyexe.fonto.common.data.mapper.toNotification
import dev.weazyexe.fonto.common.data.mapper.toPostIdList
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.model.notification.Notification
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

internal class NotificationRepository(
    private val notificationDataSource: NotificationDataSource,
    private val postRepository: PostRepository,
    private val json: Json
) {

    suspend fun getAll(): List<Notification> {
        val notificationDaos = notificationDataSource.getAll().first()
        return notificationDaos.map { dao ->
            val postIds = dao.newPostIdentifiers.toPostIdList(json)
            val posts = postRepository.getPostsByIds(postIds)
            dao.toNotification(posts)
        }
    }

    fun insert(
        newPostIdentifiers: List<Post.Id>,
        isRead: Boolean
    ) {
        notificationDataSource.insert(
            newPostIdentifiersJson = newPostIdentifiers.toJson(json),
            isRead = isRead
        )
    }

    fun update(notification: Notification) {
        notificationDataSource.update(notification.toDao(json))
    }
}