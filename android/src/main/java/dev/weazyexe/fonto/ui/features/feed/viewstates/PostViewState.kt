package dev.weazyexe.fonto.ui.features.feed.viewstates

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import dev.weazyexe.fonto.common.core.asBitmap
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.utils.HUMAN_READABLE_DATE_TIME_FORMAT
import dev.weazyexe.fonto.common.utils.format

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
    publishedAt = publishedAt?.format(HUMAN_READABLE_DATE_TIME_FORMAT),
    sourceTitle = feed.title,
    sourceIcon = feed.icon?.asBitmap(),
    isSaved = isSaved
)