package dev.weazyexe.fonto.ui.features.feed.screens.managefeed

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.State
import dev.weazyexe.fonto.ui.features.feed.viewstates.FeedViewState

@Immutable
data class ManageFeedState(
    val feedLoadState: LoadState<List<FeedViewState>> = LoadState.Loading(),
    val hasChanges: Boolean = false
): State

sealed interface ManageFeedEffect : Effect {

    data class ShowMessage(@StringRes val message: Int) : ManageFeedEffect
}
