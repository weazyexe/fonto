package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.RssDataSource
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.parser.rss.RssFeed

class RssRepository(private val rssDataSource: RssDataSource) {

    suspend fun getRssFeed(feed: Feed): RssFeed = rssDataSource.getRssFeed(feed)
}