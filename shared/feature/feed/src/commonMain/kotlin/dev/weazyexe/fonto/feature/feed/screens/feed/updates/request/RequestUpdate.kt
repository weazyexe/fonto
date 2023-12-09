package dev.weazyexe.fonto.feature.feed.screens.feed.updates.request

import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedState
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedUpdate

internal fun requestUpdate(
    message: FeedMessage.Request,
    state: FeedState
): FeedUpdate = when (message) {
    is FeedMessage.Request.GettingPosts -> gettingPostsUpdate(message, state)
    is FeedMessage.Request.UpdatingPost -> updatingPostUpdate(message, state)
    is FeedMessage.Request.GettingPostMeta -> gettingPostMetaUpdate(message, state)
}
