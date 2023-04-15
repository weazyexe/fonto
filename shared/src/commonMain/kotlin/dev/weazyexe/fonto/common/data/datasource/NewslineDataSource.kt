package dev.weazyexe.fonto.common.data.datasource

import dev.weazyexe.fonto.common.db.PostDao
import dev.weazyexe.fonto.common.utils.flowList
import dev.weazyexe.fonto.db.FontoDatabase
import kotlinx.coroutines.flow.Flow

class NewslineDataSource(database: FontoDatabase) {

    private val queries = database.postDaoQueries

    fun getAll(): Flow<List<PostDao>> = queries.getAll().flowList()

    fun insertOrUpdate(postDao: PostDao) = queries.insertOrUpdate(postDao)

    fun delete(id: String) = queries.delete(id)

    fun deleteAll() = queries.deleteAll()
}