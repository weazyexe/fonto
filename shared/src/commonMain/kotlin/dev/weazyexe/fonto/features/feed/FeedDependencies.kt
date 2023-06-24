package dev.weazyexe.fonto.features.feed

import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.data.usecase.posts.GetPostMetadataFromHtmlUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.GetPostUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.GetPostsUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.UpdatePostUseCase
import dev.weazyexe.fonto.common.feature.settings.SettingsStorage
import dev.weazyexe.fonto.utils.validator.UrlValidator

internal data class FeedDependencies(
    val initialState: FeedDomainState,

    val getPosts: GetPostsUseCase,
    val updatePost: UpdatePostUseCase,
    val getPost: GetPostUseCase,
    val getImageFromHtmlMeta: GetPostMetadataFromHtmlUseCase,

    val urlValidator: UrlValidator,
    val settingsStorage: SettingsStorage,
    val eventBus: EventBus
)