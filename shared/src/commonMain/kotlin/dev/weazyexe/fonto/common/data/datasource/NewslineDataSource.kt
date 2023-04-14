package dev.weazyexe.fonto.common.data.datasource

import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.parser.RssFeed
import dev.weazyexe.fonto.common.parser.RssParser

class NewslineDataSource {

    private val rssParser = RssParser()

    suspend fun getNewsline(feed: Feed): RssFeed {
        return rssParser.parse(feed)
    }
}