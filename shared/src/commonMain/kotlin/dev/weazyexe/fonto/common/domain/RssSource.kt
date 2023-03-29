package dev.weazyexe.fonto.common.domain

import dev.weazyexe.fonto.common.core.LocalImage

data class RssSource(
    val id: String,
    val title: String,
    val image: LocalImage?
)