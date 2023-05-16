package dev.weazyexe.fonto.common.data.datasource

import androidx.core.util.rangeTo
import dev.weazyexe.fonto.common.db.PostDao
import dev.weazyexe.fonto.common.feature.newsline.ByFeed
import dev.weazyexe.fonto.common.feature.newsline.ByPostDates
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilters
import dev.weazyexe.fonto.common.feature.newsline.OnlyBookmarksFilter
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.utils.flowList
import dev.weazyexe.fonto.db.FontoDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.offsetIn

class NewslineDataSource(database: FontoDatabase) {

    private val queries = database.postDaoQueries

    fun getAll(
        feeds: List<Feed>,
        limit: Long,
        offset: Long,
        filters: List<NewslineFilter>
    ): Flow<List<PostDao>> {
        val isSaved = filters.filterIsInstance<OnlyBookmarksFilter>()
            .firstOrNull()?.isEnabled ?: false

        val dateRange = filters.filterIsInstance<ByPostDates>().firstOrNull()?.range
        val rangeInSeconds = dateRange?.let {
            val utcOffset = it.from.offsetIn(TimeZone.currentSystemDefault()).totalSeconds
            (it.from.epochSeconds - utcOffset) rangeTo (it.to.epochSeconds - utcOffset + 86_400)
        }

        val feedsFromFilter = filters.filterIsInstance<ByFeed>().firstOrNull()?.values.orEmpty()

        return queries.getByFeedId(
            feedId = if (feedsFromFilter.isEmpty()) {
                feeds.map { it.id.origin }
            } else {
                feedsFromFilter.map { it.id.origin }
            },
            limit = limit,
            offset = offset,
            isSavedFilterEnabled = isSaved.toString(),
            isSaved = true.toString(),
            isDateRangeFilterEnabled = (dateRange != null).toString(),
            publishedFrom = rangeInSeconds?.lower?.toLong() ?: 0,
            publishedTo = rangeInSeconds?.upper?.toLong() ?: 0
        ).flowList()
    }

    fun getPostById(id: String): PostDao = queries.getPostById(id).executeAsOne()

    fun insertOrUpdate(postDao: PostDao) = queries.insertOrUpdate(postDao)

    fun insertOrIgnore(postDao: PostDao) = queries.insertOrIgnore(postDao)

    fun delete(id: String) = queries.delete(id)

    fun deletePostsFromFeed(feedId: Long) = queries.deletePostsFromFeed(feedId)

    fun deleteAll() = queries.deleteAll()

    fun getDefaultFilters(): List<NewslineFilter> = NewslineFilters
}
