package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.datasource.JsonFeedDataSource
import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.model.feed.Feed

internal class JsonFeedRepository(
    private val jsonFeedDataSource: JsonFeedDataSource
) {

    suspend fun getJsonFeed(feed: Feed): ParsedFeed = jsonFeedDataSource.getJsonFeed(feed)
}