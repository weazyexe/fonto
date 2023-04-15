package dev.weazyexe.fonto.common.data.usecase.rss

import dev.weazyexe.fonto.common.data.repository.RssRepository
import dev.weazyexe.fonto.common.model.feed.Feed

class IsRssValidUseCase(private val rssRepository: RssRepository) {

    suspend operator fun invoke(url: String): Boolean {
        val feed = Feed(id = 0, title = "", link = url, icon = null)
        return rssRepository.getRssFeed(feed).posts.isNotEmpty()
    }
}