package dev.weazyexe.fonto.ui.features.feed.components.post

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post
import kotlinx.datetime.Instant

@Immutable
data class PostViewState(
    val id: Post.Id,
    val title: String,
    val description: String,
    val link: String,
    val imageUrl: String?,
    val publishedAt: Instant,
    val feed: Feed,
    val isSaved: Boolean,
    val isRead: Boolean,
)

@Stable
fun Post.asViewState() = PostViewState(
    id = id,
    title = title,
    description = description,
    link = link,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    feed = feed,
    isSaved = isSaved,
    isRead = isRead
)

@Stable
fun PostViewState.asPost() = Post(
    id = id,
    title = title,
    description = description,
    link = link,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    feed = feed,
    isSaved = isSaved,
    isRead = isRead
)
