package dev.weazyexe.fonto.common.model.feed

import kotlinx.datetime.Instant

data class Post(
    val id: String,
    val title: String,
    val description: String,
    val content: String?,
    val imageUrl: String?,
    val publishedAt: Instant,
    val feed: Feed,
    val isSaved: Boolean
)