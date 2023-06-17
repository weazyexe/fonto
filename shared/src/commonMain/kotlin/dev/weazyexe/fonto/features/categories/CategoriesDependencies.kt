package dev.weazyexe.fonto.features.categories

import dev.weazyexe.fonto.common.data.usecase.category.DeleteCategoryUseCase
import dev.weazyexe.fonto.common.data.usecase.category.GetAllCategoriesUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetAllFeedsUseCase

internal data class CategoriesDependencies(
    val initialState: CategoriesDomainState,
    val getAllCategories: GetAllCategoriesUseCase,
    val getAllFeeds: GetAllFeedsUseCase,
    val deleteCategory: DeleteCategoryUseCase
)