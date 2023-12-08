package dev.weazyexe.fonto.feature.feed.screens.feed.updates

import dev.weazyexe.elm.noEffects
import dev.weazyexe.elm.with
import dev.weazyexe.fonto.common.DEFAULT_LIMIT
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedDependencies
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedEffects
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedState
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedUpdate
import dev.weazyexe.fonto.feature.feed.screens.feed.updates.request.requestUpdate
import dev.weazyexe.fonto.feature.feed.screens.feed.updates.view.viewUpdate

internal object FeedUpdates {

    val initialState = FeedState() with setOf(
        FeedEffects.GetPosts(
            limit = DEFAULT_LIMIT,
            offset = 0,
            useCache = false,
            shouldShowLoading = true
        )
    )

    fun restore(state: FeedState): FeedUpdate =
        state with noEffects()

    fun update(message: FeedMessage, state: FeedState, dependencies: FeedDependencies): FeedUpdate =
        when (message) {
            is FeedMessage.Request -> requestUpdate(message, state)
            is FeedMessage.View -> viewUpdate(message, state)
            is FeedMessage.OpenPost -> openPostUpdate(message, state, dependencies)
        }


}
