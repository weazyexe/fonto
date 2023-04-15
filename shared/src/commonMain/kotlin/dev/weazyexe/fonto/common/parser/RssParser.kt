package dev.weazyexe.fonto.common.parser

import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.rss.RssFeed

expect class RssParser {

    suspend fun parse(feed: Feed): RssFeed
}
