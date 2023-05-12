package dev.weazyexe.fonto.common.data.datasource

import dev.weazyexe.fonto.common.db.PostDao
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilters
import dev.weazyexe.fonto.common.feature.newsline.OnlyBookmarksFilter
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.utils.flowList
import dev.weazyexe.fonto.db.FontoDatabase
import kotlinx.coroutines.flow.Flow

class NewslineDataSource(database: FontoDatabase) {

    private val queries = database.postDaoQueries

    fun getAll(
        feeds: List<Feed>,
        limit: Long,
        offset: Long,
        filters: List<NewslineFilter>
    ): Flow<List<PostDao>> {
        val isSaved = filters.filterIsInstance<OnlyBookmarksFilter>().firstOrNull()?.value ?: false

        return queries.getByFeedId(
            feedId = feeds.map { it.id.origin },
            limit = limit,
            offset = offset,
            isSaved = isSaved.toString()
        ).flowList()
    }

    fun getPostById(id: String): PostDao = queries.getPostById(id).executeAsOne()

    fun insertOrUpdate(postDao: PostDao) = queries.insertOrUpdate(postDao)

    fun delete(id: String) = queries.delete(id)

    fun deletePostsFromFeed(feedId: Long) = queries.deletePostsFromFeed(feedId)

    fun deleteAll() = queries.deleteAll()

    fun getDefaultFilters(): List<NewslineFilter> = NewslineFilters
}
