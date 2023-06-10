package dev.weazyexe.fonto.ui.features.feed.screens.categories

import androidx.annotation.StringRes
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.State
import dev.weazyexe.fonto.ui.features.feed.components.category.CategoryViewState

data class CategoriesState(
    val categoriesLoadState: AsyncResult<List<CategoryViewState>> = AsyncResult.Loading()
) : State

sealed interface CategoriesEffect : Effect {

    data class ShowMessage(@StringRes val message: Int) : CategoriesEffect
}