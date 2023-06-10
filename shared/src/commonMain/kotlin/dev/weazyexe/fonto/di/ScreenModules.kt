package dev.weazyexe.fonto.di

import dev.weazyexe.fonto.features.addeditfeed.AddEditFeedDependencies
import dev.weazyexe.fonto.features.addeditfeed.AddEditFeedDomainState
import dev.weazyexe.fonto.features.addeditfeed.AddEditFeedPresentation
import dev.weazyexe.fonto.features.addeditfeed.AddEditFeedPresentationImpl
import dev.weazyexe.fonto.features.addeditfeed.validator.TitleValidator
import dev.weazyexe.fonto.features.addeditfeed.validator.UrlValidator
import dev.weazyexe.fonto.features.feed.FeedDependencies
import dev.weazyexe.fonto.features.feed.FeedDomainState
import dev.weazyexe.fonto.features.feed.FeedPresentation
import dev.weazyexe.fonto.features.feed.FeedPresentationImpl
import dev.weazyexe.fonto.features.managefeed.ManageFeedDependencies
import dev.weazyexe.fonto.features.managefeed.ManageFeedDomainState
import dev.weazyexe.fonto.features.managefeed.ManageFeedPresentation
import dev.weazyexe.fonto.features.managefeed.ManageFeedPresentationImpl
import dev.weazyexe.fonto.features.search.SearchDependencies
import dev.weazyexe.fonto.features.search.SearchDomainState
import dev.weazyexe.fonto.features.search.SearchPresentation
import dev.weazyexe.fonto.features.search.SearchPresentationImpl
import org.koin.core.module.Module
import org.koin.dsl.module

fun screenModules(): List<Module> = listOf(
    feedScreenModule,
    searchScreenModule,
    manageFeedScreenModule,
    addEditFeedScreenModule
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

val manageFeedScreenModule = module {
    factory { ManageFeedDomainState() }

    factory {
        ManageFeedDependencies(
            initialState = get(),
            getAllFeeds = get(),
            deleteFeed = get()
        )
    }

    factory<ManageFeedPresentation> { ManageFeedPresentationImpl(dependencies = get()) }
}

val addEditFeedScreenModule = module {
    factory { TitleValidator() }
    factory { UrlValidator() }

    factory { AddEditFeedDomainState() }

    factory {
        AddEditFeedDependencies(
            initialState = get(),
            getFeed = get(),
            createFeed = get(),
            updateFeed = get(),
            getFaviconByUrl = get(),
            getFeedType = get(),
            getAllCategories = get(),
            titleValidator = get(),
            urlValidator = get()
        )
    }

    factory<AddEditFeedPresentation> { AddEditFeedPresentationImpl(dependencies = get()) }
}