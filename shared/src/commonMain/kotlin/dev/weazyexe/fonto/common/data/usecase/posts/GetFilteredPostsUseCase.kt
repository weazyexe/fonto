package dev.weazyexe.fonto.common.data.usecase.posts

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.feature.filter.Dates
import dev.weazyexe.fonto.common.feature.posts.ByCategory
import dev.weazyexe.fonto.common.feature.posts.ByFeed
import dev.weazyexe.fonto.common.feature.posts.ByPostDates
import dev.weazyexe.fonto.common.feature.posts.ByRead
import dev.weazyexe.fonto.common.feature.posts.BySaved
import dev.weazyexe.fonto.common.feature.posts.PostsFilter
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration.Companion.days

internal class GetFilteredPostsUseCase(
    private val postRepository: PostRepository
) {

    operator fun invoke(
        query: String,
        filters: List<PostsFilter>
    ): Flow<AsyncResult<List<Post>>> = flowIo {
        emit(AsyncResult.Loading())

        val posts = postRepository.getAll()
        val filteredPosts = posts.filter { post ->
            filters.all { filter ->
                when (filter) {
                    is BySaved -> post.filterBySaved(filter.isEnabled)
                    is ByRead -> post.filterByRead(filter.isEnabled)
                    is ByCategory -> post.filterByCategory(filter.values)
                    is ByFeed -> post.filterByFeed(filter.values.map { it.id })
                    is ByPostDates -> post.filterByDates(filter.range)
                }
            } && post.containsQuery(query)
        }

        emit(AsyncResult.Success(filteredPosts))
    }

    private fun Post.filterBySaved(isFilterEnabled: Boolean): Boolean =
        !isFilterEnabled || isSaved

    private fun Post.filterByRead(isFilterEnabled: Boolean): Boolean =
        !isFilterEnabled || isRead

    private fun Post.filterByCategory(categories: List<Category>): Boolean =
        categories.isEmpty() || feed.category in categories

    private fun Post.filterByFeed(feedIds: List<Feed.Id>): Boolean =
        feedIds.isEmpty() || feed.id in feedIds

    private fun Post.filterByDates(range: Dates.Range?): Boolean {
        val isTheSameDate = range?.from == range?.to
        return range == null
                || isTheSameDate && publishedAt in range.from..(range.to + 1.days)
                || publishedAt in range.from..(range.to + 1.days)
    }

    private fun Post.containsQuery(query: String): Boolean =
        title.contains(query, ignoreCase = true)
                || description.contains(query, ignoreCase = true)
                || content?.contains(query, ignoreCase = true) == true
                || feed.title.contains(query, ignoreCase = true)
}