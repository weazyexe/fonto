package dev.weazyexe.fonto.ui.features.feed.screens.managefeed

import dev.weazyexe.fonto.ui.core.presentation.Effect
import dev.weazyexe.fonto.ui.core.presentation.LoadState
import dev.weazyexe.fonto.ui.core.presentation.State
import dev.weazyexe.fonto.ui.features.feed.viewstates.FeedViewState

data class ManageFeedState(
    val feedLoadState: LoadState<List<FeedViewState>> = LoadState.initial()
): State

sealed interface ManageFeedEffect : Effect