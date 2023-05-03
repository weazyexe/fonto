package dev.weazyexe.fonto.common.parser.atom

import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.parser.ParsedFeed

expect class AtomParser {

    suspend fun parse(feed: Feed) : ParsedFeed
}