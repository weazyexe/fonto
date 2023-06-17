package dev.weazyexe.fonto.common.feature.parser.atom

import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.model.feed.Feed

internal expect class AtomParser {

    suspend fun parse(feed: Feed) : ParsedFeed
}