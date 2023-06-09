package dev.weazyexe.fonto.ui.features.feed.screens.feed.components.search

import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.core.ui.components.filters.FilterViewState
import dev.weazyexe.fonto.ui.features.feed.components.post.PostViewState

@Immutable
data class SearchViewState(
    val query: String = "",
    val posts: AsyncResult<List<PostViewState>> = AsyncResult.Success(emptyList()),
    val filters: List<FilterViewState<NewslineFilter>> = emptyList(),
    val initialFilters: List<FilterViewState<NewslineFilter>> = emptyList(),
) {

    val areFiltersChanged: Boolean
        get() = filters != initialFilters
}
