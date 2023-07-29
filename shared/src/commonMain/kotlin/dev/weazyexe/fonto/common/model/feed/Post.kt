package dev.weazyexe.fonto.common.model.feed

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Id,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val publishedAt: Instant,
    val feed: Feed,
    val isSaved: Boolean,
    val link: String?,
    val isRead: Boolean,
    val hasTriedToLoadMetadata: Boolean,
    val addedAt: Instant,
) {

    @Serializable
    @JvmInline
    value class Id(val origin: String)
}
