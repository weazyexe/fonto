package dev.weazyexe.fonto.di

import dev.weazyexe.fonto.features.feed.FeedDependencies
import dev.weazyexe.fonto.features.feed.FeedDomainState
import dev.weazyexe.fonto.features.feed.FeedPresentation
import dev.weazyexe.fonto.features.feed.FeedPresentationImpl
import org.koin.core.module.Module
import org.koin.dsl.module

fun screenModules(): List<Module> = listOf(
    feedScreenModule
)

val feedScreenModule = module {
    factory { FeedDomainState() }

    factory {
        FeedDependencies(
            initialState = get(),
            getPosts = get(),
            updatePost = get(),
            getPost = get(),
            settingsStorage = get(),
            eventBus = get()
        )
    }

    factory<FeedPresentation> { FeedPresentationImpl(dependencies = get()) }
}