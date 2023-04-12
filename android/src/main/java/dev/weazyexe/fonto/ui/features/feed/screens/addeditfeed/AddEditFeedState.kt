package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

import android.graphics.Bitmap
import dev.weazyexe.fonto.ui.core.presentation.Effect
import dev.weazyexe.fonto.ui.core.presentation.LoadState
import dev.weazyexe.fonto.ui.core.presentation.State

data class AddEditFeedState(
    val title: String = "",
    val link: String = "",
    val iconLoadState: LoadState<Bitmap> = LoadState.initial()
) : State

sealed interface AddEditFeedEffect : Effect