package dev.weazyexe.fonto.common.parser.jsonfeed

import dev.weazyexe.fonto.common.model.feed.Feed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


sealed interface JsonFeed {

    @Serializable
    data class Success(
        @SerialName("version")
        val version: String,

        @SerialName("title")
        val title: String,

        @SerialName("items")
        val items: List<JsonFeedPost>
    )

    data class Error(
        val feed: Feed,
        val throwable: Throwable
    )
}