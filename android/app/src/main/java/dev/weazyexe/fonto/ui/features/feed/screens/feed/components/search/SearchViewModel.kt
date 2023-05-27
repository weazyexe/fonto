package dev.weazyexe.fonto.ui.features.feed.screens.feed.components.search

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.usecase.newsline.GetFiltersUseCase
import dev.weazyexe.fonto.common.feature.newsline.ByCategory
import dev.weazyexe.fonto.common.feature.newsline.ByFeed
import dev.weazyexe.fonto.common.feature.newsline.ByPostDates
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.core.ui.utils.StringResources
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getFilters: GetFiltersUseCase
) : CoreViewModel<SearchState, SearchEffect>() {

    override val initialState: SearchState = SearchState()

    init {
        loadFilters()
    }

    fun loadFilters() = viewModelScope.launch {
        val filters = getFilters()
        setState { copy(filters = filters) }
    }

    fun onQueryChange(query: String) {
        setState { copy(query = query) }
    }

    fun onActiveChange(isActive: Boolean) {
        setState { copy(isActive = isActive) }
    }

    fun applyFilters(updatedFilter: NewslineFilter) {
        val newFilters = state.filters.map {
            when (it.javaClass) {
                updatedFilter.javaClass -> updatedFilter
                else -> it
            }
        }
        setState { copy(filters = newFilters) }
        // TODO load filtered posts
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
}