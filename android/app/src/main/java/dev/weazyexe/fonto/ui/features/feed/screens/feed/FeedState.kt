package dev.weazyexe.fonto.ui.features.feed.screens.feed

import dev.weazyexe.fonto.core.ui.ScrollState
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.State
import dev.weazyexe.fonto.ui.features.feed.viewstates.NewslineViewState

data class FeedState(
    val newslineLoadState: LoadState<NewslineViewState> = LoadState.initial(),
    val scrollState: ScrollState = ScrollState()
) : State

sealed interface FeedEffect : Effect