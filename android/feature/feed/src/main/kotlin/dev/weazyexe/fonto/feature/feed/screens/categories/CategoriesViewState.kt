package dev.weazyexe.fonto.feature.feed.screens.categories

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.feature.feed.components.category.CategoryViewState

data class CategoriesViewState(
    val categories: AsyncResult<List<CategoryViewState>> = AsyncResult.Loading(),
    val hasChanges: Boolean = false
)
