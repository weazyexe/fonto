package dev.weazyexe.fonto.common.data.usecase.jsonfeed

import dev.weazyexe.fonto.common.data.repository.JsonFeedRepository
import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed

internal class IsJsonFeedValidUseCase(private val jsonFeedRepository: JsonFeedRepository) {

    suspend operator fun invoke(url: String): Boolean {
        val feed = Feed(
            id = Feed.Id(0),
            title = "",
            link = url,
            icon = null,
            type = Feed.Type.JSON_FEED,
            category = Category(Category.Id(0), ""),
            areNotificationsEnabled = false
        )
        val parsedFeed = jsonFeedRepository.getJsonFeed(feed) as? ParsedFeed.Success ?: return false
        return parsedFeed.posts.isNotEmpty()
    }
}