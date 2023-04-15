package dev.weazyexe.fonto.common.parser

import kotlinx.datetime.Instant

data class RssPost(
    val title: String,
    val link: String,
    val description: String,
    val content: String?,
    val pubDate: Instant?,
    val imageUrl: String?
)