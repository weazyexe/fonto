package dev.weazyexe.fonto.common.model.feed

import dev.weazyexe.fonto.common.model.base.LocalImage
import kotlinx.serialization.Serializable

@Serializable
data class Feed(
    val id: Id,
    val title: String,
    val link: String,
    val icon: LocalImage?
) {

    @Serializable
    @JvmInline
    value class Id(val origin: Long)
}
