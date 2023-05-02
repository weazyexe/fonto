package dev.weazyexe.fonto.common.parser.atom

import dev.weazyexe.fonto.common.model.feed.Feed

expect class AtomParser {

    suspend fun parse(feed: Feed) : AtomFeed
}