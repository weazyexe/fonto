package dev.weazyexe.fonto.features.addeditcategory

import dev.weazyexe.fonto.common.data.usecase.category.CreateCategoryUseCase
import dev.weazyexe.fonto.common.data.usecase.category.GetCategoryUseCase
import dev.weazyexe.fonto.common.data.usecase.category.UpdateCategoryUseCase
import dev.weazyexe.fonto.utils.validator.TitleValidator

internal data class AddEditCategoryDependencies(
    val initialState: AddEditCategoryDomainState,

    val getCategory: GetCategoryUseCase,
    val createCategory: CreateCategoryUseCase,
    val updateCategory: UpdateCategoryUseCase,

    val titleValidator: TitleValidator
)
