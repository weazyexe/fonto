package dev.weazyexe.fonto.common.parser.rss

import dev.weazyexe.fonto.common.model.feed.Feed

expect class RssParser {

    suspend fun parse(feed: Feed): RssFeed
}