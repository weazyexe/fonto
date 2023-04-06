package dev.weazyexe.fonto.common.data.repository

import dev.weazyexe.fonto.common.data.mapper.toFeed
import dev.weazyexe.fonto.common.domain.Feed
import dev.weazyexe.fonto.common.parser.RssParser

class FeedRepository {

    private val parser = RssParser()

    suspend fun getFeed(url: String): Feed {
        val feed = parser.parse(url)
        return feed.toFeed()
    }
}