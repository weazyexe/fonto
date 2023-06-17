package dev.weazyexe.fonto.common.model.backup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FontoBackupModel(
    @SerialName("version") val version: Int = 1,
    @SerialName("feeds") val feeds: List<FeedBackupModel>,
    @SerialName("posts") val posts: List<PostBackupModel>,
    @SerialName("categories") val categories: List<CategoryBackupModel>
)