package dev.weazyexe.fonto.feature.feed.screens.feed.updates.view

import dev.weazyexe.elm.noEffects
import dev.weazyexe.elm.with
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedEffects
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage.Request.UpdatingPost.Difference
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedState
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedUpdate
import dev.weazyexe.fonto.utils.extensions.firstOrNull

fun onPostSaveClickUpdate(
    message: FeedMessage.View.OnPostSaveClick,
    state: FeedState
): FeedUpdate {
    val post = state.posts.firstOrNull { it.id == message.id } ?: return state with noEffects()
    val difference = if (post.isSaved) {
        Difference.RemoveFromSaved
    } else {
        Difference.Save
    }

    val newPost = post.copy(isSaved = !post.isSaved)
    return state with FeedEffects.UpdatePost(newPost, difference)
}
