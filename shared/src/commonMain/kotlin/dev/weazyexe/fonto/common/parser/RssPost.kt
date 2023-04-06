package dev.weazyexe.fonto.common.parser

import kotlinx.datetime.LocalDateTime

internal data class RssPost(
    val title: String,
    val link: String,
    val description: String,
    val content: String?,
    val pubDate: LocalDateTime?,
    val imageUrl: String?
)