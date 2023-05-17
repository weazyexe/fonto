package dev.weazyexe.fonto.ui.features.feed.screens.categories

import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.State

data class CategoriesState(
    val categoriesLoadState: LoadState<List<Category>> = LoadState.Loading()
) : State

sealed interface CategoriesEffect : Effect