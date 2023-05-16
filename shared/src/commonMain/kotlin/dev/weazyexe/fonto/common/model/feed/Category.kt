package dev.weazyexe.fonto.common.model.feed

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Id,
    val title: String
) {

    @Serializable
    @JvmInline
    value class Id(val origin: Long)
}