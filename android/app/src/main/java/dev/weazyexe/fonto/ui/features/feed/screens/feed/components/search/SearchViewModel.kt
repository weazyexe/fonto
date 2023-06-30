package dev.weazyexe.fonto.ui.features.feed.screens.feed.components.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.app.FontoApplication
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.common.feature.posts.ByCategory
import dev.weazyexe.fonto.common.feature.posts.ByFeed
import dev.weazyexe.fonto.common.feature.posts.PostsFilter
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.features.search.SearchDomainState
import dev.weazyexe.fonto.features.search.SearchEffect
import dev.weazyexe.fonto.features.search.SearchPresentation
import dev.weazyexe.fonto.ui.features.feed.components.post.asViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.asViewStates
import dev.weazyexe.fonto.util.flow.mapState

class SearchViewModel(
    private val presentation: SearchPresentation,
    private val context: FontoApplication,
) : ViewModel() {

    val state = presentation.domainState.mapState { it.asViewState() }
    val effects = presentation.effects

    init {
        presentation.onCreate(viewModelScope)
    }

    fun onQueryChange(query: String) {
        presentation.onQueryChange(query)
    }

    fun applyFilters(updatedFilter: PostsFilter) {
        presentation.applyFilters(updatedFilter)
    }

    fun openPost(id: Post.Id) {
        presentation.openPost(id)
    }

    fun savePost(id: Post.Id) {
        presentation.savePost(id)
    }

    fun loadPostMetadataIfNeeds(id: Post.Id) {
        presentation.loadPostMetadataIfNeeds(id)
    }

    fun openMultiplePicker(filter: PostsFilter) {
        when (filter) {
            is ByFeed -> presentation.emit(
                SearchEffect.OpenFeedPicker(
                    values = filter.values,
                    possibleValues = filter.possibleValues
                )
            )

            is ByCategory -> presentation.emit(
                SearchEffect.OpenCategoryPicker(
                    values = filter.values,
                    possibleValues = filter.possibleValues
                )
            )

            else -> {
                // Do nothing
            }
        }
    }

    private fun SearchDomainState.asViewState(): SearchViewState =
        SearchViewState(
            query = query,
            posts = posts.map { posts -> posts.map { it.asViewState() } },
            filters = filters.asViewStates(context),
            initialFilters = initialFilters.asViewStates(context)
        )
}