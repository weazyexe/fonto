package dev.weazyexe.fonto.common.model.backup

import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedBackupModel(
    @SerialName("id") val id: Feed.Id,
    @SerialName("title") val title: String,
    @SerialName("link") val link: String,
    @SerialName("type") val type: Long,
    @SerialName("categoryId") val category: Category.Id?
)

fun Feed.asBackupModel(): FeedBackupModel =
    FeedBackupModel(
        id = id,
        title = title,
        link = link,
        type = type.id,
        category = category?.id
    )

fun FeedBackupModel.asFeed(
    category: Category,
    icon: LocalImage?
): Feed =
    Feed(
        id = id,
        title = title,
        link = link,
        type = Feed.Type.byId(type),
        category = category,
        icon = icon
    )