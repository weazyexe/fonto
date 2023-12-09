package dev.weazyexe.fonto.feature.feed.screens.feed.updates.request

import dev.weazyexe.elm.noEffects
import dev.weazyexe.elm.with
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedEffects
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage.Request.UpdatingPost.Difference
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedState
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedUpdate
import dev.weazyexe.fonto.utils.extensions.update

internal fun updatingPostUpdate(
    message: FeedMessage.Request.UpdatingPost,
    state: FeedState,
) : FeedUpdate {
    return when (message.post) {
        is AsyncResult.Loading -> updatingPostLoadingUpdate(message.post, message.difference, state)
        is AsyncResult.Error -> updatingPostErrorUpdate(message.post, message.difference, state)
        is AsyncResult.Success -> updatingPostSuccessUpdate(message.post, message.difference, state)
    }
}

private fun updatingPostLoadingUpdate(
    loading: AsyncResult.Loading<Post>,
    difference: Difference,
    state: FeedState
) : FeedUpdate {
    return state with noEffects()
}

private fun updatingPostSuccessUpdate(
    success: AsyncResult.Success<Post>,
    difference: Difference,
    state: FeedState
): FeedUpdate {
    val effects = when (difference) {
        Difference.Save -> setOf(FeedEffects.Messenger.ShowPostSavedMessage)
        Difference.RemoveFromSaved -> setOf(FeedEffects.Messenger.ShowPostRemovedFromSavedMessage)
        Difference.Read, Difference.Meta -> noEffects()
    }

    val newPosts = state.posts.update(success.data)
    return state.copy(posts = newPosts) with effects
}

private fun updatingPostErrorUpdate(
    error: AsyncResult.Error<Post>,
    difference: Difference,
    state: FeedState
): FeedUpdate {
    val effects = when (difference) {
        Difference.Save -> setOf(FeedEffects.Messenger.ShowPostSavingErrorMessage)
        Difference.RemoveFromSaved -> setOf(FeedEffects.Messenger.ShowPostRemovingFromSavedErrorMessage)
        Difference.Read, Difference.Meta -> noEffects()
    }

    return state with effects
}
