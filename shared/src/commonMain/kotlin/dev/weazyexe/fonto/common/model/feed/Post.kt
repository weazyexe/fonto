package dev.weazyexe.fonto.common.model.feed

import dev.weazyexe.fonto.common.model.base.LocalImage
import kotlinx.datetime.LocalDateTime

data class Post(
    val id: String,
    val title: String,
    val description: String,
    val content: String?,
    val imageUrl: String?,
    val publishedAt: LocalDateTime?,
    val source: String,
    val sourceIcon: LocalImage?,
    val isSaved: Boolean
)