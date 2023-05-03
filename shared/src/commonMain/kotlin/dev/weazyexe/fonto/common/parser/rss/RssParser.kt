package dev.weazyexe.fonto.common.parser.rss

import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.parser.ParsedFeed

expect class RssParser {

    suspend fun parse(feed: Feed): ParsedFeed
}