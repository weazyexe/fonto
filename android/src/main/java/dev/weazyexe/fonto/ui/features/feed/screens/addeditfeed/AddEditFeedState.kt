package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

import android.graphics.Bitmap
import dev.weazyexe.fonto.ui.core.presentation.Effect
import dev.weazyexe.fonto.ui.core.presentation.LoadState
import dev.weazyexe.fonto.ui.core.presentation.State

data class AddEditFeedState(
    val id: Long? = null,
    val title: String = "",
    val link: String = "",
    val iconLoadState: LoadState<Bitmap> = LoadState.initial(),
    val finishLoadState: LoadState<Boolean> = LoadState.initial(),
) : State

sealed interface AddEditFeedEffect : Effect {

    data class NavigateUp(val isSuccessful: Boolean) : AddEditFeedEffect
}