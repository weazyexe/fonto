package dev.weazyexe.fonto.feature.feed.screens.feed.updates.view

import dev.weazyexe.elm.noEffects
import dev.weazyexe.elm.with
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedEffects
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedState
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedUpdate
import dev.weazyexe.fonto.utils.extensions.firstOrNull

internal fun onPostClickUpdate(
    message: FeedMessage.View.OnPostClick,
    state: FeedState
): FeedUpdate {
    val post = state.posts.firstOrNull { it.id == message.id } ?: return state with noEffects()
    return state with setOf(FeedEffects.GetUrlOpenPreferences(post))
}
