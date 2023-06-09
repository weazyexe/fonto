package dev.weazyexe.fonto.features.search

import dev.weazyexe.fonto.arch.DomainState
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.model.feed.Post
import kotlinx.coroutines.flow.MutableStateFlow

data class SearchDomainState(
    val query: String = "",
    val debouncedQuery: MutableStateFlow<String> = MutableStateFlow(""),
    val posts: AsyncResult<List<Post>> = AsyncResult.Success(emptyList()),
    val filters: List<NewslineFilter> = emptyList(),
    val initialFilters: List<NewslineFilter> = emptyList(),
) : DomainState