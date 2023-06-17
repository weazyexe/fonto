package dev.weazyexe.fonto.common.model.backup

import dev.weazyexe.fonto.common.model.feed.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CategoryBackupModel(
    @SerialName("id") val id: Category.Id,
    @SerialName("title") val title: String
)

internal fun Category.asBackupModel(): CategoryBackupModel =
    CategoryBackupModel(
        id = id,
        title = title
    )

internal fun CategoryBackupModel.asCategory(): Category =
    Category(
        id = id,
        title = title
    )