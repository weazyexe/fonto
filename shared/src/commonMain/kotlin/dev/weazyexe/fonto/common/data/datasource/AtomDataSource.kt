package dev.weazyexe.fonto.common.data.datasource

import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.parser.ParsedFeed
import dev.weazyexe.fonto.common.parser.atom.AtomParser

class AtomDataSource(private val atomParser: AtomParser) {

    suspend fun getAtomFeed(feed: Feed): ParsedFeed {
        return atomParser.parse(feed)
    }
}