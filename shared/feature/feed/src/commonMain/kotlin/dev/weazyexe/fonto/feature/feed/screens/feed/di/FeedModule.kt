package dev.weazyexe.fonto.feature.feed.screens.feed.di

import dev.weazyexe.fonto.feature.feed.navigation.FeedRouter
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedDependencies
import dev.weazyexe.fonto.feature.feed.screens.feed.FeedElm
import org.koin.core.module.Module
import org.koin.dsl.module

expect val FeedPlatformModule: Module

val FeedModule = module {
    includes(FeedPlatformModule)

    factory { FeedRouter(navigator = get()) }

    factory<FeedElm> {
        FeedElm(
            FeedDependencies(
                getPosts = get(),
                updatePost = get(),
                getPostMetadataFromHtml = get(),
                urlValidator = get(),
                settingsStorage = get(),
                eventBus = get(),
                router = get(),
                messenger = get(),
                uiStrings = get()
            )
        )
    }
}
