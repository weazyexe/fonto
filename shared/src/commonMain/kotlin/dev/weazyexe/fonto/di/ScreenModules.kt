package dev.weazyexe.fonto.di

import dev.weazyexe.fonto.features.addeditcategory.AddEditCategoryDependencies
import dev.weazyexe.fonto.features.addeditcategory.AddEditCategoryDomainState
import dev.weazyexe.fonto.features.addeditcategory.AddEditCategoryPresentation
import dev.weazyexe.fonto.features.addeditcategory.AddEditCategoryPresentationImpl
import dev.weazyexe.fonto.features.addeditfeed.AddEditFeedDependencies
import dev.weazyexe.fonto.features.addeditfeed.AddEditFeedDomainState
import dev.weazyexe.fonto.features.addeditfeed.AddEditFeedPresentation
import dev.weazyexe.fonto.features.addeditfeed.AddEditFeedPresentationImpl
import dev.weazyexe.fonto.features.app.AppDependencies
import dev.weazyexe.fonto.features.app.AppDomainState
import dev.weazyexe.fonto.features.app.AppPresentation
import dev.weazyexe.fonto.features.app.AppPresentationImpl
import dev.weazyexe.fonto.features.categories.CategoriesDependencies
import dev.weazyexe.fonto.features.categories.CategoriesDomainState
import dev.weazyexe.fonto.features.categories.CategoriesPresentation
import dev.weazyexe.fonto.features.categories.CategoriesPresentationImpl
import dev.weazyexe.fonto.features.debug.DebugDependencies
import dev.weazyexe.fonto.features.debug.DebugDomainState
import dev.weazyexe.fonto.features.debug.DebugPresentation
import dev.weazyexe.fonto.features.debug.DebugPresentationImpl
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
import dev.weazyexe.fonto.features.settings.SettingsDependencies
import dev.weazyexe.fonto.features.settings.SettingsDomainState
import dev.weazyexe.fonto.features.settings.SettingsPresentation
import dev.weazyexe.fonto.features.settings.SettingsPresentationImpl
import dev.weazyexe.fonto.utils.validator.TitleValidator
import dev.weazyexe.fonto.utils.validator.UrlValidator
import org.koin.core.module.Module
import org.koin.dsl.module

fun screenModules(): List<Module> = listOf(
    appScreenModule,
    feedScreenModule,
    searchScreenModule,
    manageFeedScreenModule,
    addEditFeedScreenModule,
    categoriesScreenModule,
    addEditCategoryScreenModule,
    settingsScreenModule,
    debugScreenModule
)

val appScreenModule = module {
    factory { AppDomainState() }

    factory {
        AppDependencies(
            initialState = get(),
            settingsStorage = get(),
            eventBus = get()
        )
    }

    factory<AppPresentation> { AppPresentationImpl(dependencies = get()) }
}

val feedScreenModule = module {
    factory { FeedDomainState() }

    factory {
        FeedDependencies(
            initialState = get(),
            getPosts = get(),
            updatePost = get(),
            getPost = get(),
            getImageFromHtmlMeta = get(),
            urlValidator = get(),
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
            getFilteredPosts = get(),
            eventBus = get()
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
            deleteFeed = get(),
            eventBus = get()
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

val categoriesScreenModule = module {
    factory { CategoriesDomainState() }

    factory {
        CategoriesDependencies(
            initialState = get(),
            getAllCategories = get(),
            getAllFeeds = get(),
            deleteCategory = get()
        )
    }

    factory<CategoriesPresentation> { CategoriesPresentationImpl(dependencies = get()) }
}

val addEditCategoryScreenModule = module {
    factory { TitleValidator() }
    factory { AddEditCategoryDomainState() }

    factory {
        AddEditCategoryDependencies(
            initialState = get(),
            getCategory = get(),
            createCategory = get(),
            updateCategory = get(),
            titleValidator = get()
        )
    }

    factory<AddEditCategoryPresentation> { AddEditCategoryPresentationImpl(dependencies = get()) }
}

val settingsScreenModule = module {
    factory { SettingsDomainState() }

    factory {
        SettingsDependencies(
            initialState = get(),
            exportData = get(),
            importData = get(),
            savePreference = get(),
            getSettings = get(),
            eventBus = get(),
            featureAvailabilityChecker = get()
        )
    }

    factory<SettingsPresentation> { SettingsPresentationImpl(dependencies = get()) }
}

val debugScreenModule = module {
    factory { DebugDomainState() }

    factory {
        DebugDependencies(
            initialState = get(),
            deleteAllFeeds = get(),
            createFeed = get(),
            deleteAllPosts = get(),
            eventBus = get()
        )
    }

    factory<DebugPresentation> { DebugPresentationImpl(dependencies = get()) }
}