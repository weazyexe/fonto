package dev.weazyexe.fonto.ui.features.feed.screens.feed

import dev.weazyexe.fonto.core.ui.ScrollState
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.NewLoadState
import dev.weazyexe.fonto.core.ui.presentation.State
import dev.weazyexe.fonto.ui.features.feed.viewstates.NewslineViewState

data class FeedState(
    val newslineLoadState: NewLoadState<NewslineViewState> = NewLoadState.Loading(),
    val scrollState: ScrollState = ScrollState()
) : State

sealed interface FeedEffect : Effect