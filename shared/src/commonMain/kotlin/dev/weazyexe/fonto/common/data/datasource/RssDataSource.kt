package dev.weazyexe.fonto.common.data.datasource

import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.parser.rss.RssFeed
import dev.weazyexe.fonto.common.parser.rss.RssParser

class RssDataSource(private val rssParser: RssParser) {

    suspend fun getRssFeed(feed: Feed): RssFeed {
        return rssParser.parse(feed)
    }
}