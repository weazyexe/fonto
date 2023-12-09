package dev.weazyexe.fonto.feature.feed.screens.feed.updates.view

import dev.weazyexe.elm.noEffects
import dev.weazyexe.elm.with
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedEffects
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedState
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedUpdate

internal fun onLoadMorePostsUpdate(
    message: FeedMessage.View.OnLoadMore,
    state: FeedState
): FeedUpdate {
    if (state.posts !is AsyncResult.Success) return state with noEffects()

    val newOffset = state.offset + state.limit
    return state with setOf(
        FeedEffects.GetPosts(
            limit = state.limit,
            offset = newOffset,
            useCache = true,
            shouldShowLoading = false
        )
    )
}
