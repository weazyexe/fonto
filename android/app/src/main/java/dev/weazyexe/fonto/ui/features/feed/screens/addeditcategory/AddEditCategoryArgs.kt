package dev.weazyexe.fonto.ui.features.feed.screens.addeditcategory

import dev.weazyexe.fonto.common.model.feed.Category
import kotlinx.serialization.Serializable

@Serializable
data class AddEditCategoryArgs(
    val id: Category.Id? = null
)