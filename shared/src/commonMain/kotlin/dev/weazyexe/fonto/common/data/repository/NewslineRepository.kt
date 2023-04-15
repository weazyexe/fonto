package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.NewslineDataSource
import dev.weazyexe.fonto.common.data.mapper.toDao
import dev.weazyexe.fonto.common.data.mapper.toPostList
import dev.weazyexe.fonto.common.model.feed.Post
import kotlinx.coroutines.flow.first

class NewslineRepository(private val newslineDataSource: NewslineDataSource) {

    suspend fun getAll(): List<Post> = newslineDataSource.getAll().first().toPostList()

    fun insertOrUpdate(post: Post) = newslineDataSource.insertOrUpdate(post.toDao())

    fun delete(id: String) = newslineDataSource.delete(id)

    fun deleteAll() = newslineDataSource.deleteAll()
}