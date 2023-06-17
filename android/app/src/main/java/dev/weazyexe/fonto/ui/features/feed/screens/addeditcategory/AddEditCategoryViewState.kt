package dev.weazyexe.fonto.ui.features.feed.screens.addeditcategory

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.data.AsyncResult

@Immutable
data class AddEditCategoryViewState(
    val title: String = "",
    val isEditMode: Boolean = false,
    val savingResult: AsyncResult<Unit> = AsyncResult.Success(Unit),
    val initResult: AsyncResult<Unit> = AsyncResult.Loading(),
)