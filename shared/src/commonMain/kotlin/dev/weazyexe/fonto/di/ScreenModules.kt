package dev.weazyexe.fonto.di

import dev.weazyexe.fonto.features.feed.FeedDependencies
import dev.weazyexe.fonto.features.feed.FeedDomainState
import dev.weazyexe.fonto.features.feed.FeedPresentation
import dev.weazyexe.fonto.features.feed.FeedPresentationImpl
import dev.weazyexe.fonto.features.search.SearchDependencies
import dev.weazyexe.fonto.features.search.SearchDomainState
import dev.weazyexe.fonto.features.search.SearchPresentation
import dev.weazyexe.fonto.features.search.SearchPresentationImpl
import org.koin.core.module.Module
import org.koin.dsl.module

fun screenModules(): List<Module> = listOf(
    feedScreenModule,
    searchScreenModule
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

val searchScreenModule = module {
    factory { SearchDomainState() }

    factory {
        SearchDependencies(
            initialState = get(),
            getFilters = get(),
            getFilteredPosts = get()
        )
    }
    factory<SearchPresentation> { SearchPresentationImpl(dependencies = get()) }
}