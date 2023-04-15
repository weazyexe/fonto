package dev.weazyexe.fonto.common.model.feed

import dev.weazyexe.fonto.common.model.base.LocalImage
import kotlinx.datetime.Instant

data class Post(
    val id: String,
    val title: String,
    val description: String,
    val content: String?,
    val imageUrl: String?,
    val publishedAt: Instant?,
    val source: String,
    val sourceIcon: LocalImage?,
    val isSaved: Boolean
)