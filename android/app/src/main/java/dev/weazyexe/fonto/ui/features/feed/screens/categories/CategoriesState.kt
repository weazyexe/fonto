package dev.weazyexe.fonto.ui.features.feed.screens.categories

import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.State
import dev.weazyexe.fonto.ui.features.feed.components.category.CategoryViewState

data class CategoriesState(
    val categoriesLoadState: LoadState<List<CategoryViewState>> = LoadState.Loading()
) : State

sealed interface CategoriesEffect : Effect