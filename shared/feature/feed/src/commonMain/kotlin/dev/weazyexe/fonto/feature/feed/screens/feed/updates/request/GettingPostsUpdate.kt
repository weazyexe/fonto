package dev.weazyexe.fonto.feature.feed.screens.feed.updates.request

import dev.weazyexe.elm.noEffects
import dev.weazyexe.elm.with
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.PaginationState
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.common.model.feed.Posts
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedMessage
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedState
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedUpdate

internal fun gettingPostsUpdate(
    message: FeedMessage.Request.GettingPosts,
    state: FeedState
): FeedUpdate {
    val isPaginating = state.offset != 0
    val newState = when (message.posts) {
        is AsyncResult.Loading -> state.mapLoading(isPaginating, message.posts)
        is AsyncResult.Error -> state.mapError(isPaginating, message.posts)
        is AsyncResult.Success -> state.mapSuccess(message.posts)
    }
    return newState with noEffects()
}

private fun FeedState.mapLoading(
    isPaginating: Boolean,
    loading: AsyncResult.Loading<Posts>
): FeedState {
    return if (isPaginating) {
        copy(paginationState = PaginationState.LOADING)
    } else {
        copy(posts = loading)
    }
}

private fun FeedState.mapError(
    isPaginating: Boolean,
    error: AsyncResult.Error<Posts>
): FeedState {
    return if (isPaginating) {
        copy(paginationState = PaginationState.ERROR, isSwipeRefreshing = false)
    } else {
        copy(posts = error, isSwipeRefreshing = false)
    }
}

private fun FeedState.mapSuccess(
    success: AsyncResult.Success<Posts>
) : FeedState {
    val newPosts = success.map { Posts(posts = postsList?.posts.orEmpty() + it.posts) }
    val paginationState = if (success.data.isNotEmpty()) {
        PaginationState.IDLE
    } else {
        PaginationState.PAGINATION_EXHAUST
    }

    return copy(
        posts = newPosts,
        paginationState = paginationState,
        offset = offset + limit,
        isSwipeRefreshing = false
    )
}
