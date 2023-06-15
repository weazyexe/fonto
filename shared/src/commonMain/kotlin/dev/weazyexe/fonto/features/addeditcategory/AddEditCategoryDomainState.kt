package dev.weazyexe.fonto.features.addeditcategory

import dev.weazyexe.fonto.arch.DomainState
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.model.feed.Category

data class AddEditCategoryDomainState(
    val id: Category.Id? = null,
    val title: String = "",
    val savingResult: AsyncResult<Unit> = AsyncResult.Success(Unit),
    val initResult: AsyncResult<Unit> = AsyncResult.Loading(),
) : DomainState