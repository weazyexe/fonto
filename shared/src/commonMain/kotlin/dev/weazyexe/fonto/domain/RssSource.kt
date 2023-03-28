package dev.weazyexe.fonto.domain

import dev.weazyexe.fonto.core.LocalImage

data class RssSource(
    val id: String,
    val title: String,
    val image: LocalImage?
)