package dev.weazyexe.fonto.features.feed

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.PaginationState
import dev.weazyexe.fonto.common.data.bus.AppEvent
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.common.data.onError
import dev.weazyexe.fonto.common.data.onLoading
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.model.feed.Posts
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

internal class FeedPresentationImpl(
    private val dependencies: FeedDependencies
) : FeedPresentation() {

    override val initialState: FeedDomainState
        get() = dependencies.initialState

    override fun onCreate(scope: CoroutineScope) {
        super.onCreate(scope)
        loadPosts(isSwipeRefreshing = false)
        listenToEventBus()
    }

    override fun onSearchBarActiveChange(isActive: Boolean) {
        setState { copy(isSearchBarActive = isActive) }
    }

    override fun loadPosts(isSwipeRefreshing: Boolean) {
        dependencies.getPosts(
            limit = state.limit,
            offset = state.offset,
            shouldShowLoading = !isSwipeRefreshing,
            useCache = false
        )
            .onLoading { setState { copy(posts = it) } }
            .onError { setState { copy(posts = it, isSwipeRefreshing = false) } }
            .onSuccess { setState { copy(posts = it, isSwipeRefreshing = false) } }
            .onStart {
                setState {
                    if (isSwipeRefreshing) {
                        dependencies.initialState.copy(
                            isSwipeRefreshing = true,
                            posts = state.posts
                        )
                    } else {
                        dependencies.initialState
                    }
                }
            }
            .launchIn(scope)
    }

    override fun loadMorePosts() {
        if (state.posts !is AsyncResult.Success) return

        val newOffset = state.offset + state.limit
        dependencies.getPosts(limit = state.limit, offset = newOffset, useCache = true)
            .onLoading { setState { copy(paginationState = PaginationState.LOADING) } }
            .onError { setState { copy(paginationState = PaginationState.ERROR) } }
            .onSuccess { result ->
                setState {
                    copy(
                        posts = result.map {
                            Posts(
                                posts = state.postsList?.posts.orEmpty() + it.posts
                            )
                        },
                        paginationState = if (result.data.isNotEmpty()) {
                            PaginationState.IDLE
                        } else {
                            PaginationState.PAGINATION_EXHAUST
                        },
                        offset = newOffset
                    )
                }
            }
            .launchIn(scope)
    }

    override fun openPost(id: Post.Id) {
        dependencies.getPost(id)
            .filterNot { state.isBenchmarking }
            .filterIsInstance<AsyncResult.Success<Post>>()
            .onEach {
                val post = it.data
                val openPostPreference = dependencies.settingsStorage.getOpenPostPreference()
                val theme = dependencies.settingsStorage.getTheme()

                when (openPostPreference) {
                    OpenPostPreference.INTERNAL -> {
                        FeedEffect.OpenPostInApp(
                            link = post.link,
                            theme = theme
                        )
                    }

                    OpenPostPreference.DEFAULT_BROWSER -> {
                        FeedEffect.OpenPostInBrowser(post.link)
                    }
                }.emit()
            }
            .flatMapLatest {
                val updatedPost = it.data.copy(isRead = true)
                dependencies.updatePost(updatedPost)
            }
            .onSuccess { setState { copy(posts = posts.update(it.data)) } }
            .launchIn(scope)
    }

    override fun savePost(id: Post.Id) {
        var isSaving = false
        dependencies.getPost(id)
            .filterIsInstance<AsyncResult.Success<Post>>()
            .flatMapLatest {
                isSaving = !it.data.isSaved
                val updatedPost = it.data.copy(isSaved = !it.data.isSaved)
                dependencies.updatePost(updatedPost)
            }
            .onError {
                FeedEffect.ShowPostSavingErrorMessage(isSaving = isSaving).emit()
            }
            .onSuccess {
                setState { copy(posts = posts.update(it.data)) }
                FeedEffect.ShowPostSavedMessage(isSaving = isSaving).emit()
            }
            .launchIn(scope)
    }

    private fun listenToEventBus() {
        dependencies.eventBus.observe()
            .filter { it is AppEvent.RefreshFeed }
            .onEach { loadPosts(isSwipeRefreshing = false) }
            .launchIn(scope)
    }

    private fun AsyncResult<Posts>.update(post: Post): AsyncResult<Posts> = map { posts ->
        Posts(
            posts = posts.map { if (it.id == post.id) post else it }
        )
    }
}