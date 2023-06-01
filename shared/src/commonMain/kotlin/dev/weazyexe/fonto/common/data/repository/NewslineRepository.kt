package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.NewslineDataSource
import dev.weazyexe.fonto.common.data.mapper.toDao
import dev.weazyexe.fonto.common.data.mapper.toPost
import dev.weazyexe.fonto.common.db.PostDao
import dev.weazyexe.fonto.common.feature.newsline.ByCategory
import dev.weazyexe.fonto.common.feature.newsline.ByFeed
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post
import kotlinx.coroutines.flow.first

class NewslineRepository(
    private val newslineDataSource: NewslineDataSource,
    private val feedRepository: FeedRepository,
    private val categoryRepository: CategoryRepository,
) {

    suspend fun getAll(): List<Post> {
        val feeds = feedRepository.getAll()
        val postDaos = newslineDataSource.getAll().first()
        return postDaos.mergeWithFeeds(feeds)
    }

    suspend fun getPosts(
        limit: Int,
        offset: Int
    ): List<Post> {
        val feeds = feedRepository.getAll()
        val postDaos = newslineDataSource.getPosts(
            feeds = feeds,
            limit = limit.toLong(),
            offset = offset.toLong()
        ).first()

        return postDaos.mergeWithFeeds(feeds)
    }

    fun getPostById(id: Post.Id, feed: Feed): Post =
        newslineDataSource.getPostById(id.origin).toPost(feed)

    fun insertOrUpdate(post: Post) = newslineDataSource.insertOrUpdate(post.toDao())

    fun insertOrIgnore(post: Post) = newslineDataSource.insertOrIgnore(post.toDao())

    fun delete(id: String) = newslineDataSource.delete(id)

    fun deletePostsFromFeed(feedId: Long) = newslineDataSource.deletePostsFromFeed(feedId)

    fun deleteAll() = newslineDataSource.deleteAll()

    suspend fun composeFilters(): List<NewslineFilter> {
        val feeds = feedRepository.getAll()
        val categories = categoryRepository.getAll()
        return newslineDataSource
            .getDefaultFilters()
            .map { filter ->
                when (filter) {
                    is ByFeed -> filter.change(
                        newValue = emptyList(),
                        newPossibleValues = feeds.map { ByFeed.Data(it.id, it.title) }
                    )

                    is ByCategory -> filter.change(
                        newValue = emptyList(),
                        newPossibleValues = categories
                    )

                    else -> filter
                }
            }
    }

    private fun List<PostDao>.mergeWithFeeds(feeds: List<Feed>): List<Post> {
        return feeds
            .flatMap { feed ->
                this
                    .filter { it.feedId == feed.id.origin }
                    .map { it.toPost(feed) }
            }
            .sortedByDescending { it.publishedAt }
    }
}
