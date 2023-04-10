package dev.weazyexe.fonto.common.data.datasource

import dev.weazyexe.fonto.common.db.FeedDao
import dev.weazyexe.fonto.common.extensions.flowList
import dev.weazyexe.fonto.db.FontoDatabase
import kotlinx.coroutines.flow.Flow

class FeedDataSource(database: FontoDatabase) {

    private val queries = database.feedDaoQueries

    fun getAll(): Flow<List<FeedDao>> = queries.getAll().flowList()

    fun insert(title: String, link: String, icon: ByteArray?) = queries.insert(title, link, icon)

    fun update(feed: FeedDao) = queries.update(feed)

    fun delete(id: Long) = queries.delete(id)

    fun deleteAll() = queries.deleteAll()
}