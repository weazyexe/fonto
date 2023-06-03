package dev.weazyexe.fonto.common.model.backup

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FontoBackupModel(
    @SerialName("feeds") val feeds: List<FeedBackupModel>,
    @SerialName("posts") val posts: List<PostBackupModel>,
    @SerialName("categories") val categories: List<CategoryBackupModel>
)