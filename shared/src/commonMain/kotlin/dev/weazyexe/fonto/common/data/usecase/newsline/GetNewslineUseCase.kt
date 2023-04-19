package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.mapper.toPosts
import dev.weazyexe.fonto.common.data.repository.NewslineRepository
import dev.weazyexe.fonto.common.data.repository.RssRepository
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Newsline
import dev.weazyexe.fonto.common.model.rss.RssFeed
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class GetNewslineUseCase(
    private val newslineRepository: NewslineRepository,
    private val rssRepository: RssRepository,
) {

    suspend operator fun invoke(feeds: List<Feed>): Newsline {
        val rssFeeds = coroutineScope {
            feeds.map {
                async { rssRepository.getRssFeed(it) }
            }.awaitAll()
        }

        val problematicFeedList = mutableListOf<RssFeed.Error>()
        rssFeeds
            .map {
                when (it) {
                    is RssFeed.Success -> it.toPosts()
                    is RssFeed.Error -> {
                        problematicFeedList.add(it)
                        emptyList()
                    }
                }
            }
            .flatten()
            .forEach { newslineRepository.insertOrUpdate(it) }

        return Newsline(
            posts = newslineRepository.getAll(feeds),
            loadedWithError = problematicFeedList
        )
    }
}