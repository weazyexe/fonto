package dev.weazyexe.fonto.common.feature.parser.atom

import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.model.feed.Feed

internal actual class AtomParser {

    actual suspend fun parse(feed: Feed): ParsedFeed {
        return ParsedFeed.Error(
            feed = feed,
            throwable = IllegalStateException("Atom parser is not implemented")
        )
    }
}