@file:JvmName("CommonModules")

package dev.weazyexe.fonto.common.di

import dev.weazyexe.fonto.common.app.AppInitializer
import dev.weazyexe.fonto.common.app.CategoriesInitializer
import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.data.datasource.AtomDataSource
import dev.weazyexe.fonto.common.data.datasource.CategoryDataSource
import dev.weazyexe.fonto.common.data.datasource.FeedDataSource
import dev.weazyexe.fonto.common.data.datasource.IconDataSource
import dev.weazyexe.fonto.common.data.datasource.PostDataSource
import dev.weazyexe.fonto.common.data.datasource.RssDataSource
import dev.weazyexe.fonto.common.data.repository.AtomRepository
import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.IconRepository
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.data.repository.RssRepository
import dev.weazyexe.fonto.common.data.usecase.atom.IsAtomValidUseCase
import dev.weazyexe.fonto.common.data.usecase.backup.GetExportDataUseCase
import dev.weazyexe.fonto.common.data.usecase.category.CreateCategoryUseCase
import dev.weazyexe.fonto.common.data.usecase.category.DeleteCategoryUseCase
import dev.weazyexe.fonto.common.data.usecase.category.GetAllCategoriesUseCase
import dev.weazyexe.fonto.common.data.usecase.category.GetCategoryUseCase
import dev.weazyexe.fonto.common.data.usecase.category.UpdateCategoryUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.ChangeFeedCategoryUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.CreateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.DeleteAllFeedsUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.DeleteFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetAllFeedsUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedIconUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedTypeUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.UpdateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.icon.GetIconByRssUrlUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetFilteredPostsUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetFiltersUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetNewslineUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetPaginatedNewslineUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetPostUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.UpdatePostUseCase
import dev.weazyexe.fonto.common.data.usecase.rss.IsRssValidUseCase
import dev.weazyexe.fonto.common.db.createDatabase
import dev.weazyexe.fonto.common.feature.parser.atom.AtomParser
import dev.weazyexe.fonto.common.feature.parser.rss.RssParser
import dev.weazyexe.fonto.common.feature.settings.createSettingsStorage
import dev.weazyexe.fonto.common.network.createHttpClient
import dev.weazyexe.fonto.common.resources.createStringsProvider
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformModule(): Module

internal val coreModule = module {
    includes(platformModule())

    single { createDatabase(get()) }
    single { createHttpClient() }
    single { createSettingsStorage(get()) }
    single { createStringsProvider(get())  }

    single { EventBus() }
}

internal val rssModule = module {
    includes(coreModule)

    single { RssParser() }
    single { RssDataSource(get()) }
    single { RssRepository(get()) }
    single { IsRssValidUseCase(get()) }
}

internal val atomModule = module {
    includes(coreModule)

    single { AtomParser() }
    single { AtomDataSource(get()) }
    single { AtomRepository(get()) }
    single { IsAtomValidUseCase(get()) }
}

internal val iconModule = module {
    includes(coreModule)

    single { IconDataSource(get()) }
    single { IconRepository(get()) }
    single { GetIconByRssUrlUseCase(get()) }
}

internal val categoryModule = module {
    includes(coreModule)

    single { CategoryDataSource(get()) }
    single { CategoryRepository(get()) }
    single { GetAllCategoriesUseCase(get()) }
    single { GetCategoryUseCase(get()) }
    single { CreateCategoryUseCase(get()) }
    single { UpdateCategoryUseCase(get()) }
    single { DeleteCategoryUseCase(get(), get(), get()) }
    single { ChangeFeedCategoryUseCase(get()) }
}

internal val feedModule = module {
    includes(coreModule)
    includes(rssModule)
    includes(atomModule)
    includes(categoryModule)

    single { FeedDataSource(get()) }
    single { FeedRepository(get(), get()) }

    single { GetAllFeedsUseCase(get()) }
    single { CreateFeedUseCase(get()) }
    single { UpdateFeedUseCase(get()) }
    single { DeleteFeedUseCase(get(), get()) }
    single { DeleteAllFeedsUseCase(get(), get()) }
    single { GetFeedIconUseCase(get()) }
    single { GetFeedTypeUseCase(get(), get()) }
}

internal val postModule = module {
    includes(coreModule)
    includes(rssModule)
    includes(atomModule)
    includes(feedModule)

    single { PostDataSource(get()) }
    single { PostRepository(get(), get(), get()) }
    single { GetNewslineUseCase(get(), get(), get(), get()) }
    single { GetFilteredPostsUseCase(get()) }
    single { GetFiltersUseCase(get()) }
    single { GetPaginatedNewslineUseCase(get()) }
    single { GetPostUseCase(get(), get()) }
    single { UpdatePostUseCase(get()) }
}

internal val backupModule = module {
    includes(coreModule, feedModule, categoryModule, postModule)
    single { GetExportDataUseCase(get(), get(), get()) }
}

internal val initializerModule = module {
    includes(coreModule)
    includes(categoryModule)

    single { CategoriesInitializer(get(), get(), get()) }
    single { AppInitializer(get()) }
}

fun appModules(): List<Module> =
    listOf(
        initializerModule,
        feedModule,
        iconModule,
        rssModule,
        atomModule,
        postModule,
        categoryModule,
        backupModule
    )
