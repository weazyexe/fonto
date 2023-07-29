package dev.weazyexe.fonto.features.search

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.bus.AppEvent
import dev.weazyexe.fonto.common.data.onError
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.feature.posts.PostsFilter
import dev.weazyexe.fonto.common.html.OgMetadata
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import dev.weazyexe.fonto.utils.extensions.firstOrNull
import dev.weazyexe.fonto.utils.extensions.update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class SearchPresentationImpl(private val dependencies: SearchDependencies) :
    SearchPresentation() {

    override val initialState: SearchDomainState
        get() = dependencies.initialState

    override fun onCreate(scope: CoroutineScope) {
        super.onCreate(scope)
        loadFilters()
        subscribeOnQueryChange()
        listenToEventBus()
    }

    override fun onQueryChange(query: String) {
        setState { copy(query = query) }
        state.debouncedQuery.value = query
    }

    override fun applyFilters(updatedFilter: PostsFilter) {
        val newFilters = state.filters.map {
            when (it.javaClass) {
                updatedFilter.javaClass -> updatedFilter
                else -> it
            }
        }
        setState { copy(filters = newFilters) }
        loadFilteredPosts()
    }

    override fun openPost(id: Post.Id) {
        scope.launch {
            val post = state.posts.firstOrNull { it.id == id } ?: return@launch

            if (post.link == null || !dependencies.urlValidator.validate(post.link)) {
                SearchEffect.ShowInvalidLinkMessage.emit()
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
                SearchEffect.ShowPostSavingErrorMessage(isSaving = updatedPost.isSaved).emit()
            }
            .onSuccess {
                val newPosts = state.posts.update(it.data)
                setState { copy(posts = newPosts) }
                SearchEffect.ShowPostSavedMessage(isSaving = updatedPost.isSaved).emit()
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

    override fun emit(effect: SearchEffect) {
        effect.emit()
    }

    private fun loadFilters() {
        dependencies.getFilters()
            .onSuccess {
                setState {
                    copy(
                        filters = it.data,
                        initialFilters = it.data
                    )
                }
            }
            .launchIn(scope)
    }

    private fun loadFilteredPosts() {
        dependencies.getAllPosts(state.query, state.filters)
            .onEach { postsResult -> setState { copy(posts = postsResult) } }
            .launchIn(scope)
    }

    private suspend fun openPost(link: String) {
        val openPostPreference = dependencies.settingsStorage.getOpenPostPreference()
        val theme = dependencies.settingsStorage.getTheme()

        when (openPostPreference) {
            OpenPostPreference.INTERNAL -> {
                SearchEffect.OpenPostInApp(
                    link = link,
                    theme = theme
                )
            }

            OpenPostPreference.DEFAULT_BROWSER -> {
                SearchEffect.OpenPostInBrowser(link)
            }
        }.emit()
    }

    private fun subscribeOnQueryChange() {
        state.debouncedQuery
            .filter { it.isNotEmpty() }
            .onEach { setState { copy(posts = AsyncResult.Loading()) } }
            .debounce(500L)
            .onEach { loadFilteredPosts() }
            .launchIn(scope)
    }

    private fun listenToEventBus() {
        dependencies.eventBus.observe()
            .filterIsInstance<AppEvent.RefreshFeed>()
            .onEach { loadFilters() }
            .launchIn(scope)
    }
}