package dev.weazyexe.fonto.common.domain

import kotlinx.datetime.LocalDateTime

data class Post(
    val id: String,
    val title: String,
    val description: String,
    val content: String?,
    val imageUrl: String?,
    val publishedAt: LocalDateTime?,
    val source: RssSource,
    val isSaved: Boolean
)