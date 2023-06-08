package dev.weazyexe.fonto.features.feed

import dev.weazyexe.fonto.common.data.usecase.newsline.GetPostsUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.UpdatePostUseCase
import dev.weazyexe.fonto.common.feature.settings.SettingsStorage

data class FeedDependencies(
    val initialState: FeedDomainState,

    val getPosts: GetPostsUseCase,
    val updatePost: UpdatePostUseCase,

    val settingsStorage: SettingsStorage
)