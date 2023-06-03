package dev.weazyexe.fonto.common.model.backup

import dev.weazyexe.fonto.common.model.feed.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryBackupModel(
    @SerialName("id") val id: Category.Id,
    @SerialName("title") val title: String
)

fun Category.asBackupModel(): CategoryBackupModel =
    CategoryBackupModel(
        id = id,
        title = title
    )

fun CategoryBackupModel.asCategory(): Category =
    Category(
        id = id,
        title = title
    )