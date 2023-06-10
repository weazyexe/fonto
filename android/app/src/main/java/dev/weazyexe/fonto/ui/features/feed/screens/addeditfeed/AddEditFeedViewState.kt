package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.model.feed.Category

@Immutable
data class AddEditFeedViewState(
    val title: String = "",
    val link: String = "",
    val category: Category? = null,
    val isEditMode: Boolean = false,
    val categories: List<Category> = emptyList(),
    val icon: AsyncResult<ImageBitmap?> = AsyncResult.Success(null),
    val finishResult: AsyncResult<Unit> = AsyncResult.Success(Unit)
)
