package dev.weazyexe.fonto.feature.feed.screens.feed.updates.request

import dev.weazyexe.elm.noEffects
import dev.weazyexe.elm.with
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedState
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedUpdate
import dev.weazyexe.fonto.utils.extensions.update

internal fun updatingPostUpdate(
    message: FeedMessage.Request.UpdatingPost,
    state: FeedState
) : FeedUpdate {
    return when (message.post) {
        is AsyncResult.Loading -> state with noEffects()
        is AsyncResult.Error -> state with noEffects()
        is AsyncResult.Success -> {
            val newPosts = state.posts.update(message.post.data)
            state.copy(posts = newPosts) with noEffects()
        }
    }
}
