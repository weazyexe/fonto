package dev.weazyexe.fonto.android.feature.feed.screens.feed

import dev.weazyexe.elm.AndroidElmViewModel
import dev.weazyexe.fonto.android.feature.feed.viewstates.asViewState
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedDependencies
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedElm
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedState

class FeedViewModel(elm: FeedElm) :
    AndroidElmViewModel<FeedState, FeedViewState, FeedMessage, FeedDependencies>(elm) {

    override fun convertToViewState(state: FeedState): FeedViewState = state.asViewState()

    private fun FeedState.asViewState(): FeedViewState =
        FeedViewState(
            posts = posts.map { it.asViewState() },
            paginationState = paginationState,
            isSwipeRefreshing = isSwipeRefreshing,
            isSearchBarActive = isSearchBarActive,
        )
}
