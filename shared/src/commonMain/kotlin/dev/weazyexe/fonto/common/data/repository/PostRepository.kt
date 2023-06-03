package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.PostDataSource
import dev.weazyexe.fonto.common.data.mapper.toDao
import dev.weazyexe.fonto.common.data.mapper.toPost
import dev.weazyexe.fonto.common.db.PostDao
import dev.weazyexe.fonto.common.feature.newsline.ByCategory
import dev.weazyexe.fonto.common.feature.newsline.ByFeed
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post
import kotlinx.coroutines.flow.first

class PostRepository(
    private val postDataSource: PostDataSource,
    private val feedRepository: FeedRepository,
    private val categoryRepository: CategoryRepository,
) {

    suspend fun getAll(): List<Post> {
        val feeds = feedRepository.getAll()
        val postDaos = postDataSource.getAll().first()
        return postDaos.mergeWithFeeds(feeds)
    }

    suspend fun getPosts(
        limit: Int,
        offset: Int
    ): List<Post> {
        val feeds = feedRepository.getAll()
        val postDaos = postDataSource.getPosts(
            feeds = feeds,
            limit = limit.toLong(),
            offset = offset.toLong()
        ).first()

        return postDaos.mergeWithFeeds(feeds)
    }

    fun getPostById(id: Post.Id, feed: Feed): Post =
        postDataSource.getPostById(id.origin).toPost(feed)

    fun insertOrUpdate(post: Post) = postDataSource.insertOrUpdate(post.toDao())

    fun insertOrIgnore(post: Post) = postDataSource.insertOrIgnore(post.toDao())

    fun delete(id: String) = postDataSource.delete(id)

    fun deletePostsFromFeed(feedId: Long) = postDataSource.deletePostsFromFeed(feedId)

    fun deleteAll() = postDataSource.deleteAll()

    suspend fun composeFilters(): List<NewslineFilter> {
        val feeds = feedRepository.getAll()
        val categories = categoryRepository.getAll()
        return postDataSource
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
