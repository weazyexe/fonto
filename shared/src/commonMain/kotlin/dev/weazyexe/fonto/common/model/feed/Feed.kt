package dev.weazyexe.fonto.common.model.feed

import dev.weazyexe.fonto.common.model.base.LocalImage

data class Feed(
    val id: Long,
    val title: String,
    val link: String,
    val icon: LocalImage?
)