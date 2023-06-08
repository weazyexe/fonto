package dev.weazyexe.fonto.features.feed

import dev.weazyexe.fonto.arch.Presentation
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.PaginationState
import dev.weazyexe.fonto.common.model.feed.Posts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class FeedPresentation(
    override val scope: CoroutineScope,
    private val dependencies: FeedDependencies
) : Presentation<FeedDomainState, FeedEffect>() {

    override val initialState: FeedDomainState
        get() = dependencies.initialState

    override fun onCreate() {
        startPostsLoading()
    }

    fun startPostsLoading() {
        dependencies.getPosts(limit = state.limit, offset = state.offset, useCache = false)
            .map { setState { copy(posts = it) } }
            .onStart { setState { dependencies.initialState } }
            .launchIn(scope)
    }

    fun loadMorePosts() {
        val newOffset = state.offset + state.limit
        dependencies.getPosts(limit = state.limit, offset = newOffset, useCache = true)
            .map { result ->
                setState {
                    when (result) {
                        is AsyncResult.Loading -> copy(
                            paginationState = PaginationState.LOADING
                        )

                        is AsyncResult.Error -> copy(
                            paginationState = PaginationState.ERROR
                        )

                        is AsyncResult.Success -> copy(
                            posts = result.mergeWithOldPosts(posts),
                            paginationState = if (result.data.isNotEmpty()) {
                                PaginationState.IDLE
                            } else {
                                PaginationState.PAGINATION_EXHAUST
                            },
                            offset = newOffset
                        )
                    }
                }
            }
            .launchIn(scope)
    }

    private fun AsyncResult.Success<Posts>.mergeWithOldPosts(oldPosts: AsyncResult<Posts>): AsyncResult<Posts> {
        return when (oldPosts) {
            is AsyncResult.Error -> oldPosts
            is AsyncResult.Loading -> oldPosts
            is AsyncResult.Success -> AsyncResult.Success(
                Posts(
                    posts = oldPosts.data.posts + data.posts,
                    loadedWithError = data.loadedWithError
                )
            )
        }
    }
}