package dev.weazyexe.fonto.features.search

import dev.weazyexe.fonto.arch.DomainState
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.feature.posts.PostsFilter
import dev.weazyexe.fonto.common.model.feed.Posts
import kotlinx.coroutines.flow.MutableStateFlow

data class SearchDomainState(
    val query: String = "",
    val debouncedQuery: MutableStateFlow<String> = MutableStateFlow(""),
    val posts: AsyncResult<Posts> = AsyncResult.Success(Posts.EMPTY),
    val filters: List<PostsFilter> = emptyList(),
    val initialFilters: List<PostsFilter> = emptyList(),
) : DomainState