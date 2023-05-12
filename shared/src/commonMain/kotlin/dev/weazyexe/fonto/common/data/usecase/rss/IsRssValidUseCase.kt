package dev.weazyexe.fonto.common.data.usecase.rss

import dev.weazyexe.fonto.common.data.repository.RssRepository
import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.model.feed.Feed

class IsRssValidUseCase(private val rssRepository: RssRepository) {

    suspend operator fun invoke(url: String): Boolean {
        val feed = Feed(id = Feed.Id(0), title = "", link = url, icon = null, type = Feed.Type.RSS)
        val parsedFeed = rssRepository.getRssFeed(feed) as? ParsedFeed.Success ?: return false
        return parsedFeed.posts.isNotEmpty()
    }
}
