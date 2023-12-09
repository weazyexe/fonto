package dev.weazyexe.fonto.feature.feed.screens.feed.updates.view

import dev.weazyexe.elm.with
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedEffects
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedState
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedUpdate

fun onManageFeedClickUpdate(
    message: FeedMessage.View.OnManageFeedClick,
    state: FeedState
) : FeedUpdate {
    return state with setOf(FeedEffects.Navigation.OpenManageFeed)
}
