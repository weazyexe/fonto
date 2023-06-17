package dev.weazyexe.fonto.common.feature.parser

import dev.weazyexe.fonto.common.model.feed.Feed
import kotlinx.datetime.Instant

internal data class ParsedPost(
    val title: String,
    val link: String,
    val description: String,
    val pubDate: Instant,
    val imageUrl: String?,
    val feed: Feed,
)