package dev.weazyexe.fonto.ui.screens.feed.viewstates

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import dev.weazyexe.fonto.core.asBitmap
import dev.weazyexe.fonto.domain.Post
import dev.weazyexe.fonto.extensions.format

@Immutable
data class PostViewState(
    val id: String,
    val title: String,
    val description: String,
    val content: String?,
    val imageUrl: String?,
    val publishedAt: String?,
    val sourceTitle: String,
    val sourceIcon: Bitmap?,
    val isSaved: Boolean,
)

@Stable
fun Post.asViewState() = PostViewState(
    id = id,
    title = title,
    description = description,
    content = content,
    imageUrl = imageUrl,
    publishedAt = publishedAt?.format(),
    sourceTitle = source.title,
    sourceIcon = source.image?.asBitmap(),
    isSaved = isSaved
)