package dev.weazyexe.fonto.common.data.datasource

import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.feature.parser.rss.RssParser
import dev.weazyexe.fonto.common.model.feed.Feed

internal class RssDataSource(private val rssParser: RssParser) {

    suspend fun getRssFeed(feed: Feed): ParsedFeed {
        return rssParser.parse(feed)
    }
}