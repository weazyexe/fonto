package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.feature.filter.Dates
import dev.weazyexe.fonto.common.feature.newsline.ByCategory
import dev.weazyexe.fonto.common.feature.newsline.ByFeed
import dev.weazyexe.fonto.common.feature.newsline.ByPostDates
import dev.weazyexe.fonto.common.feature.newsline.ByRead
import dev.weazyexe.fonto.common.feature.newsline.BySaved
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post
import kotlin.time.Duration.Companion.days

class GetFilteredPostsUseCase(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(
        query: String,
        filters: List<NewslineFilter>
    ): List<Post> {
        val posts = postRepository.getAll()

        return posts.filter { post ->
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
                || publishedAt in range.from..range.to
    }

    private fun Post.containsQuery(query: String): Boolean =
        title.contains(query, ignoreCase = true)
                || description.contains(query, ignoreCase = true)
                || content?.contains(query, ignoreCase = true) == true
                || feed.title.contains(query, ignoreCase = true)
}