package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.NewslineDataSource
import dev.weazyexe.fonto.common.data.mapper.toDao
import dev.weazyexe.fonto.common.data.mapper.toPost
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post
import kotlinx.coroutines.flow.first

class NewslineRepository(
    private val newslineDataSource: NewslineDataSource
) {

    suspend fun getAll(
        feeds: List<Feed>,
        limit: Int,
        offset: Int,
        filters: List<NewslineFilter>
    ): List<Post> {
        val postDaos = newslineDataSource.getAll(
            feeds = feeds,
            limit = limit.toLong(),
            offset = offset.toLong(),
            filters = filters
        ).first()

        return feeds
            .flatMap { feed ->
                postDaos
                    .filter { it.feedId == feed.id.origin }
                    .map { it.toPost(feed) }
            }
            .sortedByDescending { it.publishedAt }
    }

    fun getPostById(id: Post.Id, feed: Feed): Post =
        newslineDataSource.getPostById(id.origin).toPost(feed)

    fun insertOrUpdate(post: Post) = newslineDataSource.insertOrUpdate(post.toDao())

    fun delete(id: String) = newslineDataSource.delete(id)

    fun deletePostsFromFeed(feedId: Long) = newslineDataSource.deletePostsFromFeed(feedId)

    fun deleteAll() = newslineDataSource.deleteAll()

    fun getDefaultFilters(): List<NewslineFilter> = newslineDataSource.getDefaultFilters()
}
