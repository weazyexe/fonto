package dev.weazyexe.fonto.common.model.feed

import dev.weazyexe.fonto.common.model.base.LocalImage
import kotlinx.serialization.Serializable

@Serializable
data class Feed(
    val id: Id,
    val title: String,
    val link: String,
    val icon: LocalImage?,
    val type: Type,
    val category: Category?
) {

    @Serializable
    @JvmInline
    value class Id(val origin: Long)

    enum class Type(val id: Long) {
        RSS(0),
        ATOM(1),
        JSON_FEED(2);

        companion object {

            fun byId(id: Long): Type = values().first { it.id == id }
        }
    }
}
