package dev.weazyexe.fonto.common.data.datasource

import dev.weazyexe.fonto.common.db.NotificationDao
import dev.weazyexe.fonto.common.model.notification.Notification
import dev.weazyexe.fonto.common.utils.flowList
import dev.weazyexe.fonto.db.FontoDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

internal class NotificationDataSource(database: FontoDatabase) {

    private val queries = database.notificationDaoQueries

    fun getAll(): Flow<List<NotificationDao>> = queries.getAll().flowList()

    fun getById(id: Notification.Id): NotificationDao? =
        queries.getById(id.origin).executeAsOneOrNull()

    fun insert(
        id: Notification.Id,
        type: Notification.Type,
        meta: String,
        isRead: Boolean,
        createdAt: Instant
    ) {
        queries.insert(
            id = id.origin,
            type = type.origin,
            isRead = isRead.toString(),
            createdAt = createdAt.epochSeconds,
            meta = meta
        )
    }

    fun update(dao: NotificationDao) {
        queries.update(dao)
    }
}