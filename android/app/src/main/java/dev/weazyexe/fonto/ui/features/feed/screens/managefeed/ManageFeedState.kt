package dev.weazyexe.fonto.ui.features.feed.screens.managefeed

import androidx.annotation.StringRes
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.State
import dev.weazyexe.fonto.ui.features.feed.viewstates.FeedViewState

data class ManageFeedState(
    val feedLoadState: LoadState<List<FeedViewState>> = LoadState.initial()
): State

sealed interface ManageFeedEffect : Effect {

    data class ShowMessage(@StringRes val message: Int) : ManageFeedEffect
}