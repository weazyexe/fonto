package dev.weazyexe.fonto.common.data.datasource

import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.feature.parser.atom.AtomParser
import dev.weazyexe.fonto.common.model.feed.Feed

class AtomDataSource(private val atomParser: AtomParser) {

    suspend fun getAtomFeed(feed: Feed): ParsedFeed {
        return atomParser.parse(feed)
    }
}