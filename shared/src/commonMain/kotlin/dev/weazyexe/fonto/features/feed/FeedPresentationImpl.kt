package dev.weazyexe.fonto.features.feed

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.PaginationState
import dev.weazyexe.fonto.common.data.bus.AppEvent
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.common.data.onError
import dev.weazyexe.fonto.common.data.onLoading
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.html.OgMetadata
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.model.feed.Posts
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

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
            .filterIsInstance<AsyncResult.Success<Post>>()
            .onEach {
                val post = it.data

                if (post.link == null || !dependencies.urlValidator.validate(post.link)) {
                    FeedEffect.ShowInvalidLinkMessage.emit()
                    error("Post link is invalid: ${post.link}")
                }

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
            .catch { it.printStackTrace() }
            .flatMapLatest {
                val updatedPost = it.data.copy(isRead = true)
                dependencies.updatePost(updatedPost)
            }
            .onSuccess {
                val newPosts = state.posts.update(it.data)
                setState { copy(posts = newPosts) }
            }
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
                val newPosts = state.posts.update(it.data)
                setState { copy(posts = newPosts) }
                FeedEffect.ShowPostSavedMessage(isSaving = isSaving).emit()
            }
            .launchIn(scope)
    }

    override fun loadImageIfNeeds(id: Post.Id) {
        val post = state.posts.firstOrNull { it.id == id } ?: return
        if (post.link == null || post.imageUrl != null) return

        dependencies.getImageFromHtmlMeta(post.link)
            .filterIsInstance<AsyncResult.Success<OgMetadata>>()
            .map {
                val newPost = post.copy(
                    description = post.description.ifBlank {
                        it.data.description.orEmpty()
                    },
                    imageUrl = it.data.imageUrl,
                    hasTriedToLoadMetadata = true
                )
                val newPosts = state.posts.update(newPost)
                setState { copy(posts = newPosts) }
                newPost
            }
            .flatMapLatest { dependencies.updatePost(it) }
            .launchIn(scope)
    }

    private fun listenToEventBus() {
        dependencies.eventBus.observe()
            .filter { it is AppEvent.RefreshFeed }
            .onEach { loadPosts(isSwipeRefreshing = false) }
            .launchIn(scope)
    }

    private suspend fun AsyncResult<Posts>.update(post: Post): AsyncResult<Posts> {
        return withContext(Dispatchers.Default) {
            map { posts ->
                Posts(
                    posts = posts.map { if (it.id == post.id) post else it }
                )
            }
        }
    }

    private fun AsyncResult<Posts>.firstOrNull(predicate: (Post) -> Boolean): Post? {
        val postsResult = this as? AsyncResult.Success ?: return null
        return postsResult.data.posts.firstOrNull { predicate(it) }
    }
}