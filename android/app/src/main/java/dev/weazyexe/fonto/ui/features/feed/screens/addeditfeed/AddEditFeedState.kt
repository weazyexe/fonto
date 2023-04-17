package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

import android.graphics.Bitmap
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.State

data class AddEditFeedState(
    val id: Long? = null,
    val title: String = "",
    val link: String = "",
    val iconLoadState: LoadState<Bitmap?> = LoadState.Data(null),
    val finishLoadState: LoadState<Unit> = LoadState.Data(Unit)
) : State

sealed interface AddEditFeedEffect : Effect {

    data class NavigateUp(val isSuccessful: Boolean) : AddEditFeedEffect
}