package dev.weazyexe.fonto.ui.features.feed.screens.categories

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.ui.features.feed.components.category.CategoryViewState

data class CategoriesViewState(
    val categories: AsyncResult<List<CategoryViewState>> = AsyncResult.Loading()
)