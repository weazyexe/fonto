package dev.weazyexe.fonto.features.search

import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.data.usecase.posts.GetAllPostsUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.GetFiltersUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.GetPostMetadataFromHtmlUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.UpdatePostUseCase
import dev.weazyexe.fonto.common.feature.settings.SettingsStorage
import dev.weazyexe.fonto.utils.validator.UrlValidator

internal data class SearchDependencies(
    val initialState: SearchDomainState,

    val updatePost: UpdatePostUseCase,
    val getFilters: GetFiltersUseCase,
    val getAllPosts: GetAllPostsUseCase,
    val getPostMetadataFromHtml: GetPostMetadataFromHtmlUseCase,

    val urlValidator: UrlValidator,
    val settingsStorage: SettingsStorage,
    val eventBus: EventBus
)