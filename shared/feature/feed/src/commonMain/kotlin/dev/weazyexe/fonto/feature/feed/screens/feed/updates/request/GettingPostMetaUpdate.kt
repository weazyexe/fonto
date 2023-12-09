package dev.weazyexe.fonto.feature.feed.screens.feed.updates.request

import dev.weazyexe.elm.noEffects
import dev.weazyexe.elm.with
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedEffects
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage.Request.UpdatingPost.Difference
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedState
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedUpdate

fun gettingPostMetaUpdate(
    message: FeedMessage.Request.GettingPostMeta,
    state: FeedState
) : FeedUpdate {
    return when(message.meta) {
        is AsyncResult.Error -> state with noEffects()
        is AsyncResult.Loading -> state with noEffects()
        is AsyncResult.Success -> {
            val post = message.post
            val newPost = post.copy(
                description = post.description.ifBlank {
                    message.meta.data.description.orEmpty()
                },
                imageUrl = message.meta.data.imageUrl,
                hasTriedToLoadMetadata = true
            )

            return state with setOf(FeedEffects.UpdatePost(newPost, Difference.Meta))
        }
    }
}
