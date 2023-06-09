package dev.weazyexe.fonto.features.search

import dev.weazyexe.fonto.common.data.usecase.newsline.GetFilteredPostsUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetFiltersUseCase

internal data class SearchDependencies(
    val initialState: SearchDomainState,

    val getFilters: GetFiltersUseCase,
    val getFilteredPosts: GetFilteredPostsUseCase
)