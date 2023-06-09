package dev.weazyexe.fonto.features.search

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.model.feed.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class SearchPresentationImpl(private val dependencies: SearchDependencies) : SearchPresentation() {

    override val initialState: SearchDomainState
        get() = dependencies.initialState

    override fun onCreate(scope: CoroutineScope) {
        super.onCreate(scope)
        loadFilters()
        subscribeOnQueryChange()
    }

    override fun onQueryChange(query: String) {
        setState { copy(query = query) }
        state.debouncedQuery.value = query
    }

    override fun applyFilters(updatedFilter: NewslineFilter) {
        val newFilters = state.filters.map {
            when (it.javaClass) {
                updatedFilter.javaClass -> updatedFilter
                else -> it
            }
        }
        setState { copy(filters = newFilters) }
        loadFilteredPosts()
    }

    override fun onPostRead(id: Post.Id) {
        val post = state.posts.findById(id) ?: return
        setState { copy(posts = posts.update(post.copy(isRead = true))) }
    }

    override fun onPostSave(id: Post.Id) {
        val post = state.posts.findById(id) ?: return
        setState { copy(posts = posts.update(post.copy(isSaved = !post.isSaved))) }
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
        dependencies.getFilteredPosts(state.query, state.filters)
            .onEach { setState { copy(posts = it) } }
            .launchIn(scope)
    }

    private fun subscribeOnQueryChange() {
        state.debouncedQuery
            .filter { it.isNotEmpty() }
            .onEach { setState { copy(posts = AsyncResult.Loading()) } }
            .debounce(500L)
            .onEach { loadFilteredPosts() }
            .launchIn(scope)
    }

    private fun AsyncResult<List<Post>>.findById(id: Post.Id): Post? = (this as? AsyncResult.Success)?.data?.firstOrNull { it.id == id }

    private fun AsyncResult<List<Post>>.update(updatedPost: Post): AsyncResult<List<Post>> = map { posts ->
        posts.map {
            if (it.id == updatedPost.id) {
                updatedPost
            } else {
                it
            }
        }
    }
}