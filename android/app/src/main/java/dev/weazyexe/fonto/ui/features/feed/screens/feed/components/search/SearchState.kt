package dev.weazyexe.fonto.ui.features.feed.screens.feed.components.search

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.feature.newsline.ByFeed
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.presentation.State
import kotlinx.coroutines.flow.MutableStateFlow

@Immutable
data class SearchState(
    val query: String = "",
    val debouncedQuery: MutableStateFlow<String> = MutableStateFlow(""),
    val isActive: Boolean = false,
    val postsLoadState: LoadState<List<Post>> = LoadState.Data(emptyList()),
    val filters: List<NewslineFilter> = emptyList(),
    val initialFilters: List<NewslineFilter> = emptyList(),
) : State {

    val areFiltersChanged: Boolean
        get() = filters != initialFilters
}

sealed interface SearchEffect : Effect {

    object OpenDateRangePicker : SearchEffect

    data class OpenFeedPicker(
        val values: List<ByFeed.Data>,
        val possibleValues: List<ByFeed.Data>,
        @StringRes val title: Int
    ) : SearchEffect

    data class OpenCategoryPicker(
        val values: List<Category>,
        val possibleValues: List<Category>,
        @StringRes val title: Int
    ) : SearchEffect
}