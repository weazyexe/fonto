package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

import android.graphics.Bitmap
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.State

@Immutable
data class AddEditFeedState(
    val id: Feed.Id? = null,
    val title: String = "",
    val link: String = "",
    val category: Category? = null,
    val categories: List<Category> = emptyList(),
    val iconLoadState: LoadState<Bitmap?> = LoadState.Data(null),
    val finishLoadState: LoadState<Unit> = LoadState.Data(Unit)
) : State

sealed interface AddEditFeedEffect : Effect {

    data class NavigateUp(val isSuccessful: Boolean) : AddEditFeedEffect

    data class ShowMessage(@StringRes val message: Int) : AddEditFeedEffect
}
