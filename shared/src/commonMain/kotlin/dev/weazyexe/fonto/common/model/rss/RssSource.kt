package dev.weazyexe.fonto.common.model.rss

import dev.weazyexe.fonto.common.model.base.LocalImage

data class RssSource(
    val id: Long,
    val title: String,
    val image: LocalImage?
)