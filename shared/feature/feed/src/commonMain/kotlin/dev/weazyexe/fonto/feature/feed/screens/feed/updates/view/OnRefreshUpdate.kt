package dev.weazyexe.fonto.feature.feed.screens.feed.updates.view

import dev.weazyexe.elm.with
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedEffects
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedState
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedUpdate
import dev.weazyexe.fonto.feature.feed.screens.feed.updates.FeedUpdates

internal fun onRefreshUpdate(
    message: FeedMessage.View.OnRefresh,
    state: FeedState
): FeedUpdate {
    val restoredState = FeedUpdates.initialState.state.copy(
        isSwipeRefreshing = true,
        posts = state.posts
    )

    return restoredState with setOf(
        FeedEffects.GetPosts(
            limit = state.limit,
            offset = 0,
            useCache = false,
            shouldShowLoading = false
        )
    )
}
