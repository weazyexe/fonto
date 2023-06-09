package dev.weazyexe.fonto.ui.features.feed.screens.feed.components.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.app.App
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.common.feature.newsline.ByCategory
import dev.weazyexe.fonto.common.feature.newsline.ByFeed
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.features.search.SearchDomainState
import dev.weazyexe.fonto.features.search.SearchEffect
import dev.weazyexe.fonto.features.search.SearchPresentation
import dev.weazyexe.fonto.ui.features.feed.components.post.asViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.asViewStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchViewModel(
    private val presentation: SearchPresentation,
    private val context: App,
) : ViewModel() {

    val state: Flow<SearchViewState>
        get() = presentation.domainState.map { it.asViewState() }

    val effects = presentation.effects

    init {
        presentation.onCreate(viewModelScope)
    }

    fun onQueryChange(query: String) {
        presentation.onQueryChange(query)
    }

    fun applyFilters(updatedFilter: NewslineFilter) {
        presentation.applyFilters(updatedFilter)
    }

    fun onPostRead(id: Post.Id) {
        presentation.onPostRead(id)
    }

    fun onPostSave(id: Post.Id) {
        presentation.onPostSave(id)
    }

    fun openMultiplePicker(filter: NewslineFilter) {
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