package dev.weazyexe.fonto.common.model.backup

import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostBackupModel(
    @SerialName("id") val id: Post.Id,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("link") val link: String?,
    @SerialName("feedId") val feedId: Feed.Id,
    @SerialName("publishedAt") val publishedAt: Long,
    @SerialName("isRead") val isRead: Boolean,
    @SerialName("isSaved") val isSaved: Boolean,
    @SerialName("imageUrl") val imageUrl: String?,
    @SerialName("addedAt") val addedAt: Long?
)

internal fun Post.asBackupModel(): PostBackupModel =
    PostBackupModel(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        link = link,
        feedId = feed.id,
        publishedAt = publishedAt.epochSeconds,
        isRead = isRead,
        isSaved = isSaved,
        addedAt = addedAt.epochSeconds
    )

internal fun PostBackupModel.asPost(feed: Feed): Post =
    Post(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        link = link,
        feed = feed,
        publishedAt = Instant.fromEpochSeconds(publishedAt),
        isRead = isRead,
        isSaved = isSaved,
        hasTriedToLoadMetadata = imageUrl != null && description.isNotBlank(),
        addedAt = addedAt?.let { Instant.fromEpochSeconds(it) } ?: Clock.System.now() // TODO fix, make it required param
    )