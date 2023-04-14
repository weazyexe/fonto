package dev.weazyexe.fonto.common.parser

import dev.weazyexe.fonto.common.model.base.LocalImage

data class RssFeed(
    val id: Long,
    val title: String,
    val link: String,
    val description: String,
    val posts: List<RssPost>,
    val icon: LocalImage?
)