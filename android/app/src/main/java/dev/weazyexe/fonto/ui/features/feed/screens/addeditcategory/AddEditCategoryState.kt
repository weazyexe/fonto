package dev.weazyexe.fonto.ui.features.feed.screens.addeditcategory

import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.State

data class AddEditCategoryState(
    val id: Category.Id? = null,
    val title: String = "",
    val savingLoadState: LoadState<Unit> = LoadState.Data(Unit),
    val initLoadState: LoadState<Unit> = LoadState.Data(Unit),
) : State

sealed interface AddEditCategoryEffect : Effect {

    object NavigateUp : AddEditCategoryEffect
}