package dev.weazyexe.fonto.features.feed

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.PaginationState
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.common.data.onError
import dev.weazyexe.fonto.common.data.onLoading
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.html.OgMetadata
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.model.feed.Posts
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import dev.weazyexe.fonto.utils.extensions.firstOrNull
import dev.weazyexe.fonto.utils.extensions.update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class FeedPresentationImpl(
    private val dependencies: FeedDependencies
) : FeedPresentation() {

    override val initialState: FeedDomainState
        get() = dependencies.initialState

    override fun onCreate(scope: CoroutineScope) {
        super.onCreate(scope)
        loadPosts(isSwipeRefreshing = false)
    }

    override fun onSearchBarActiveChange(isActive: Boolean) {
        setState { copy(isSearchBarActive = isActive) }
    }

    override fun loadPosts(isSwipeRefreshing: Boolean) {
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

        dependencies.getPosts(
            limit = state.limit,
            offset = state.offset,
            shouldShowLoading = !isSwipeRefreshing,
            useCache = false
        )
            .onLoading { setState { copy(posts = it) } }
            .onError { setState { copy(posts = it, isSwipeRefreshing = false) } }
            .onSuccess { setState { copy(posts = it, isSwipeRefreshing = false) } }
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
        scope.launch {
            val post = state.posts.firstOrNull { it.id == id } ?: return@launch

            if (post.link == null || !dependencies.urlValidator.validate(post.link)) {
                FeedEffect.ShowInvalidLinkMessage.emit()
                error("Post link is invalid: ${post.link}")
            }

            openPost(link = post.link)
            val updatedPost = post.copy(isRead = true)

            dependencies.updatePost(updatedPost)
                .onSuccess {
                    val newPosts = state.posts.update(it.data)
                    setState { copy(posts = newPosts) }
                }
                .launchIn(this)
        }
    }

    override fun savePost(id: Post.Id) {
        val post = state.posts.firstOrNull { it.id == id } ?: return
        val updatedPost = post.copy(isSaved = !post.isSaved)

        dependencies.updatePost(updatedPost)
            .onError {
                FeedEffect.ShowPostSavingErrorMessage(isSaving = updatedPost.isSaved).emit()
            }
            .onSuccess {
                val newPosts = state.posts.update(it.data)
                setState { copy(posts = newPosts) }
                FeedEffect.ShowPostSavedMessage(isSaving = updatedPost.isSaved).emit()
            }
            .launchIn(scope)
    }

    override fun loadPostMetadataIfNeeds(id: Post.Id) {
        val post = state.posts.firstOrNull { it.id == id } ?: return
        if (post.link == null || post.imageUrl != null) return

        dependencies.getPostMetadataFromHtml(post.link)
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

    override fun onScroll(firstVisibleItemIndex: Int, firstVisibleItemOffset: Int) {
        setState {
            copy(
                firstVisibleItemIndex = firstVisibleItemIndex,
                firstVisibleItemOffset = firstVisibleItemOffset
            )
        }
    }

    override fun scrollToTop() {
        FeedEffect.ScrollToTop.emit()
    }

    private suspend fun openPost(link: String) {
        val openPostPreference = dependencies.settingsStorage.getOpenPostPreference()
        val theme = dependencies.settingsStorage.getTheme()

        when (openPostPreference) {
            OpenPostPreference.INTERNAL -> {
                FeedEffect.OpenPostInApp(
                    link = link,
                    theme = theme
                )
            }

            OpenPostPreference.DEFAULT_BROWSER -> {
                FeedEffect.OpenPostInBrowser(link)
            }
        }.emit()
    }
}
