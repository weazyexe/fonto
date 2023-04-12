package dev.weazyexe.fonto.common.model.feed

import kotlinx.datetime.LocalDateTime

data class Post(
    val id: String,
    val title: String,
    val description: String,
    val content: String?,
    val imageUrl: String?,
    val publishedAt: LocalDateTime?,
    val isSaved: Boolean
)