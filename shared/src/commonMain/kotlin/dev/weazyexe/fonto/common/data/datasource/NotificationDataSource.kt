package dev.weazyexe.fonto.common.data.datasource

import dev.weazyexe.fonto.common.db.NotificationDao
import dev.weazyexe.fonto.common.utils.flowList
import dev.weazyexe.fonto.db.FontoDatabase
import kotlinx.coroutines.flow.Flow

internal class NotificationDataSource(database: FontoDatabase) {

    private val queries = database.notificationDaoQueries

    fun getAll(): Flow<List<NotificationDao>> = queries.getAll().flowList()

    fun insert(
        newPostIdentifiersJson: String,
        isRead: Boolean
    ) {
        queries.insert(
            newPostIdentifiers = newPostIdentifiersJson,
            isRead = isRead.toString()
        )
    }

    fun update(dao: NotificationDao) {
        queries.update(dao)
    }
}