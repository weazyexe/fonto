package dev.weazyexe.fonto.ui.features.feed.screens.feed

import dev.weazyexe.fonto.ui.core.presentation.Effect
import dev.weazyexe.fonto.ui.core.presentation.LoadState
import dev.weazyexe.fonto.ui.core.presentation.State
import dev.weazyexe.fonto.ui.features.feed.viewstates.NewslineViewState

data class FeedState(
    val newslineLoadState: LoadState<NewslineViewState> = LoadState.initial()
) : State

sealed interface FeedEffect : Effect