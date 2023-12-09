package dev.weazyexe.fonto.feature.feed.screens.feed.updates.view

import dev.weazyexe.elm.noEffects
import dev.weazyexe.elm.with
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedState
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedUpdate

fun onSearchBarActiveChangeUpdate(
    message: FeedMessage.View.OnSearchBarActiveChange,
    state: FeedState
) : FeedUpdate {
    return state.copy(isSearchBarActive = message.isActive) with noEffects()
}
