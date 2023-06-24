package dev.weazyexe.fonto.common.data.datasource

import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.feature.parser.jsonfeed.JsonFeedParser
import dev.weazyexe.fonto.common.model.feed.Feed

internal class JsonFeedDataSource(
    private val jsonFeedParser: JsonFeedParser
) {

    suspend fun getJsonFeed(feed: Feed): ParsedFeed {
        return jsonFeedParser.parse(feed)
    }
}