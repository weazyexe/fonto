package dev.weazyexe.fonto.features.addeditcategory

import dev.weazyexe.fonto.arch.NavigationArguments
import dev.weazyexe.fonto.common.model.feed.Category

data class AddEditCategoryArgs(
    val id: Category.Id?
) : NavigationArguments