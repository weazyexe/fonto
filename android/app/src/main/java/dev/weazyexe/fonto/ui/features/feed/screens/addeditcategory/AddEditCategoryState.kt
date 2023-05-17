package dev.weazyexe.fonto.ui.features.feed.screens.addeditcategory

import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.State

data class AddEditCategoryState(
    val id: Category.Id? = null,
    val title: String = "",
) : State

sealed interface AddEditCategoryEffect : Effect