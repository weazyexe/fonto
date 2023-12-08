package dev.weazyexe.fonto.android.feature.feed.screens.categories

import dev.weazyexe.fonto.android.feature.feed.components.category.CategoryViewState
import dev.weazyexe.fonto.common.data.AsyncResult

data class CategoriesViewState(
    val categories: AsyncResult<List<CategoryViewState>> = AsyncResult.Loading(),
    val hasChanges: Boolean = false
)
