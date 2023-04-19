package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.annotation.StringRes
import dev.weazyexe.fonto.common.DEFAULT_LIMIT
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.core.ui.ScrollState
import dev.weazyexe.fonto.core.ui.pagination.PaginationState
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.State
import dev.weazyexe.fonto.ui.features.feed.viewstates.NewslineViewState

data class FeedState(
    val newslineLoadState: LoadState<NewslineViewState> = LoadState.Loading.Default(),
    val newslinePaginationState: PaginationState = PaginationState.IDLE,
    val scrollState: ScrollState = ScrollState(),
    val feeds: List<Feed> = emptyList(),
    val limit: Int = DEFAULT_LIMIT,
    val offset: Int = 0,
    val canPaginate: Boolean = true
) : State

sealed interface FeedEffect : Effect {

    class ShowMessage(@StringRes val message: Int, vararg val args: Any) : FeedEffect
}