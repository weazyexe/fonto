package dev.weazyexe.fonto.feature.feed.screens.feed

import dev.weazyexe.elm.Elm
import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.data.usecase.posts.GetPostMetadataFromHtmlUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.GetPostsUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.UpdatePostUseCase
import dev.weazyexe.fonto.common.feature.settings.SettingsStorage
import dev.weazyexe.fonto.feature.feed.navigation.FeedRouter
import dev.weazyexe.fonto.feature.feed.screens.feed.updates.FeedUpdates
import dev.weazyexe.fonto.utils.validator.UrlValidator
import dev.weazyexe.messenger.Messenger

class FeedElm(dependencies: FeedDependencies) :
    Elm<FeedState, FeedMessage, FeedDependencies>(
        init = FeedUpdates.initialState,
        update = FeedUpdates::update,
        restore = FeedUpdates::restore,
        dependencies = dependencies
    )

data class FeedDependencies(
    val getPosts: GetPostsUseCase,
    val updatePost: UpdatePostUseCase,
    val getPostMetadataFromHtml: GetPostMetadataFromHtmlUseCase,

    val settingsStorage: SettingsStorage,
    val urlValidator: UrlValidator,
    val eventBus: EventBus,

    val navigator: FeedRouter,
    val messenger: Messenger,
    val uiMessages: FeedUiMessages
)

