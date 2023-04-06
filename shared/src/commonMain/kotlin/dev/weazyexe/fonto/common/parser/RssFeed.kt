package dev.weazyexe.fonto.common.parser

internal data class RssFeed(
    val title: String,
    val link: String,
    val description: String,
    val posts: List<RssPost>
)