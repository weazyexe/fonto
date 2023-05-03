package dev.weazyexe.fonto.common.parser

import dev.weazyexe.fonto.common.model.feed.Feed
import kotlinx.datetime.Instant

data class ParsedPost(
    val title: String,
    val link: String,
    val description: String,
    val content: String?,
    val pubDate: Instant,
    val imageUrl: String?,
    val feed: Feed,
)