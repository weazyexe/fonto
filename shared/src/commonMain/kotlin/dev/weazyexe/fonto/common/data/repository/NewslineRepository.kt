package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.NewslineDataSource
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.parser.RssFeed
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class NewslineRepository(private val newslineDataSource: NewslineDataSource) {

    suspend fun getNewsline(feed: Feed): RssFeed {
        return newslineDataSource.getNewsline(feed)
    }

    suspend fun getNewslines(feeds: Collection<Feed>): List<RssFeed> {
        return coroutineScope {
            feeds.map {
                async { newslineDataSource.getNewsline(it) }
            }.awaitAll()
        }
    }
}