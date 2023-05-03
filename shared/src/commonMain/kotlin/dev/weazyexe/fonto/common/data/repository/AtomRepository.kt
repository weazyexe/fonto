package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.AtomDataSource
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.parser.ParsedFeed

class AtomRepository(private val atomDataSource: AtomDataSource) {

    suspend fun getAtomFeed(feed: Feed): ParsedFeed = atomDataSource.getAtomFeed(feed)
}