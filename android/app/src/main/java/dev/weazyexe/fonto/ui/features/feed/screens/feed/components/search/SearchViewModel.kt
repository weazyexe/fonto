package dev.weazyexe.fonto.ui.features.feed.screens.feed.components.search

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.usecase.newsline.GetFilteredPostsUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetFiltersUseCase
import dev.weazyexe.fonto.common.feature.newsline.ByCategory
import dev.weazyexe.fonto.common.feature.newsline.ByFeed
import dev.weazyexe.fonto.common.feature.newsline.ByPostDates
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.ui.features.feed.components.post.PostViewState
import dev.weazyexe.fonto.ui.features.feed.components.post.asViewState
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getFilters: GetFiltersUseCase,
    private val getFilteredPosts: GetFilteredPostsUseCase
) : CoreViewModel<SearchState, SearchEffect>() {

    override val initialState: SearchState = SearchState()

    init {
        loadFilters()
        subscribeOnQueryChange()
    }

    fun onQueryChange(query: String) {
        setState { copy(query = query) }
        state.debouncedQuery.value = query
    }

    fun applyFilters(updatedFilter: NewslineFilter) {
        val newFilters = state.filters.map {
            when (it.javaClass) {
                updatedFilter.javaClass -> updatedFilter
                else -> it
            }
        }
        setState { copy(filters = newFilters) }
        loadFilteredPosts()
    }

    fun openDateRangePicker(filter: NewslineFilter) {
        when (filter) {
            is ByPostDates -> SearchEffect.OpenDateRangePicker.emit()
            else -> {
                // do nothing
            }
        }
    }

    fun openMultiplePicker(filter: NewslineFilter) {
        when (filter) {
            is ByFeed -> SearchEffect.OpenFeedPicker(
                values = filter.values,
                possibleValues = filter.possibleValues,
                title = StringResources.feed_filters_sources
            ).emit()

            is ByCategory -> SearchEffect.OpenCategoryPicker(
                values = filter.values,
                possibleValues = filter.possibleValues,
                title = StringResources.feed_filters_categories
            ).emit()

            else -> {
                // Do nothing
            }
        }
    }

    fun onPostSave(post: PostViewState) {
        (state.postsLoadState as? LoadState.Data)?.data?.let { posts ->
            val updatedPosts = posts.map {
                if (it.id == post.id) {
                    it.copy(isSaved = !post.isSaved)
                } else {
                    it
                }
            }
            setState { copy(postsLoadState = LoadState.Data(updatedPosts)) }
        }
    }

    private fun loadFilters() = viewModelScope.launch {
        val filters = getFilters()
        setState {
            copy(
                filters = filters,
                initialFilters = filters
            )
        }
    }

    private fun loadFilteredPosts() = viewModelScope.launch {
        setState { copy(postsLoadState = LoadState.Loading()) }

        val posts = request { getFilteredPosts(state.query, state.filters) }
            .withErrorHandling {
                setState { copy(postsLoadState = LoadState.Error(it)) }
            }?.data ?: return@launch

        setState { copy(postsLoadState = LoadState.Data(posts.map { it.asViewState() })) }
    }

    private fun subscribeOnQueryChange() {
        state.debouncedQuery
            .filter { it.isNotEmpty() }
            .onEach { setState { copy(postsLoadState = LoadState.Loading()) } }
            .debounce(500L)
            .onEach { loadFilteredPosts() }
            .launchInViewModelScope()
    }
}