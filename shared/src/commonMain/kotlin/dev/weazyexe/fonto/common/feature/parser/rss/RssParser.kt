package dev.weazyexe.fonto.common.feature.parser.rss

import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.model.feed.Feed

internal expect class RssParser {

    suspend fun parse(feed: Feed): ParsedFeed
}