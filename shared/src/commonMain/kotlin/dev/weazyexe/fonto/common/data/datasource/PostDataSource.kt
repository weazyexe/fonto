package dev.weazyexe.fonto.common.data.datasource

import dev.weazyexe.fonto.common.db.PostDao
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilters
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.utils.flowList
import dev.weazyexe.fonto.db.FontoDatabase
import kotlinx.coroutines.flow.Flow

class PostDataSource(database: FontoDatabase) {

    private val queries = database.postDaoQueries

    fun getAll(): Flow<List<PostDao>> = queries.getAll().flowList()

    fun getPosts(
        feeds: List<Feed>,
        limit: Long,
        offset: Long
    ): Flow<List<PostDao>> =
        queries.getByFeedId(
            feedId = feeds.map { it.id.origin },
            limit = limit,
            offset = offset
        ).flowList()

    fun getPostById(id: String): PostDao = queries.getPostById(id).executeAsOne()

    fun insertOrUpdate(postDao: PostDao) = queries.insertOrUpdate(postDao)

    fun insertOrIgnore(postDao: PostDao) = queries.insertOrIgnore(postDao)

    fun delete(id: String) = queries.delete(id)

    fun deletePostsFromFeed(feedId: Long) = queries.deletePostsFromFeed(feedId)

    fun deleteAll() = queries.deleteAll()

    fun getDefaultFilters(): List<NewslineFilter> = NewslineFilters
}
