package dev.weazyexe.fonto.features.search

import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.data.usecase.posts.GetFilteredPostsUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.GetFiltersUseCase

internal data class SearchDependencies(
    val initialState: SearchDomainState,

    val getFilters: GetFiltersUseCase,
    val getFilteredPosts: GetFilteredPostsUseCase,

    val eventBus: EventBus
)