package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.AtomDataSource
import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.model.feed.Feed

class AtomRepository(private val atomDataSource: AtomDataSource) {

    suspend fun getAtomFeed(feed: Feed): ParsedFeed = atomDataSource.getAtomFeed(feed)
}