@file:JvmName("CommonDataModules")

package dev.weazyexe.fonto.di

import dev.weazyexe.fonto.common.app.background.createPlatformWorkManager
import dev.weazyexe.fonto.common.app.background.sync.SyncPostsWorker
import dev.weazyexe.fonto.common.app.background.sync.SyncPostsWorkerImpl
import dev.weazyexe.fonto.common.app.initializer.AppInitializer
import dev.weazyexe.fonto.common.app.initializer.AppInitializerImpl
import dev.weazyexe.fonto.common.app.initializer.CategoriesInitializer
import dev.weazyexe.fonto.common.app.initializer.MockFeedInitializer
import dev.weazyexe.fonto.common.app.initializer.SyncPostsInitializer
import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.data.datasource.AtomDataSource
import dev.weazyexe.fonto.common.data.datasource.CategoryDataSource
import dev.weazyexe.fonto.common.data.datasource.FeedDataSource
import dev.weazyexe.fonto.common.data.datasource.IconDataSource
import dev.weazyexe.fonto.common.data.datasource.JsonFeedDataSource
import dev.weazyexe.fonto.common.data.datasource.PostDataSource
import dev.weazyexe.fonto.common.data.datasource.RssDataSource
import dev.weazyexe.fonto.common.data.repository.AtomRepository
import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.IconRepository
import dev.weazyexe.fonto.common.data.repository.JsonFeedRepository
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.data.repository.RssRepository
import dev.weazyexe.fonto.common.data.usecase.atom.IsAtomValidUseCase
import dev.weazyexe.fonto.common.data.usecase.backup.ExportDataUseCase
import dev.weazyexe.fonto.common.data.usecase.backup.GetExportDataUseCase
import dev.weazyexe.fonto.common.data.usecase.backup.ImportDataUseCase
import dev.weazyexe.fonto.common.data.usecase.backup.ParseBackupDataUseCase
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
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedTypeUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.UpdateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.icon.GetFaviconByUrlUseCase
import dev.weazyexe.fonto.common.data.usecase.jsonfeed.IsJsonFeedValidUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.DeleteAllPostsUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.GetFilteredPostsUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.GetFiltersUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.GetPostMetadataFromHtmlUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.GetPostUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.GetPostsUseCase
import dev.weazyexe.fonto.common.data.usecase.posts.UpdatePostUseCase
import dev.weazyexe.fonto.common.data.usecase.rss.IsRssValidUseCase
import dev.weazyexe.fonto.common.data.usecase.settings.GetDefaultSettingsUseCase
import dev.weazyexe.fonto.common.data.usecase.settings.GetSettingsUseCase
import dev.weazyexe.fonto.common.data.usecase.settings.SavePreferenceUseCase
import dev.weazyexe.fonto.common.db.createDatabase
import dev.weazyexe.fonto.common.feature.parser.atom.AtomParser
import dev.weazyexe.fonto.common.feature.parser.jsonfeed.JsonFeedParser
import dev.weazyexe.fonto.common.feature.parser.rss.RssParser
import dev.weazyexe.fonto.common.feature.settings.createSettingsStorage
import dev.weazyexe.fonto.common.html.createOgImageExtractor
import dev.weazyexe.fonto.common.network.createHttpClient
import dev.weazyexe.fonto.common.resources.createStringsProvider
import dev.weazyexe.fonto.common.serialization.createJson
import dev.weazyexe.fonto.common.serialization.createXml
import dev.weazyexe.fonto.utils.feature.FeatureAvailabilityChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformModule(): Module

fun dataModules(): List<Module> =
    listOf(
        initializerSharedModule,
        backgroundTasksSharedModule,
        feedSharedModule,
        iconSharedModule,
        rssSharedModule,
        atomSharedModule,
        jsonFeedSharedModule,
        postSharedModule,
        categorySharedModule,
        backupSharedModule,
        settingsSharedModule,
    )

internal val coreModule = module {
    includes(platformModule())

    single { createDatabase(get()) }
    single { createHttpClient() }
    single { createSettingsStorage(get()) }
    single { createStringsProvider(get()) }
    single { createOgImageExtractor(get()) }
    single { createPlatformWorkManager(get()) }

    single { createXml() }
    single { createJson() }

    single { FeatureAvailabilityChecker() }

    single { EventBus() }

    single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
}

internal val rssSharedModule = module {
    includes(coreModule)

    single { RssParser(get(), get()) }
    single { RssDataSource(get()) }
    single { RssRepository(get()) }
    single { IsRssValidUseCase(get()) }
}

internal val atomSharedModule = module {
    includes(coreModule)

    single { AtomParser(get(), get()) }
    single { AtomDataSource(get()) }
    single { AtomRepository(get()) }
    single { IsAtomValidUseCase(get()) }
}

internal val jsonFeedSharedModule = module {
    includes(coreModule)

    single { JsonFeedParser(get(), get()) }
    single { JsonFeedDataSource(get()) }
    single { JsonFeedRepository(get()) }
    single { IsJsonFeedValidUseCase(get()) }
}

internal val iconSharedModule = module {
    includes(coreModule)

    single { IconDataSource(get()) }
    single { IconRepository(get()) }
    single { GetFaviconByUrlUseCase(get()) }
}

internal val categorySharedModule = module {
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

internal val feedSharedModule = module {
    includes(coreModule)
    includes(rssSharedModule)
    includes(atomSharedModule)
    includes(jsonFeedSharedModule)
    includes(categorySharedModule)

    single { FeedDataSource(get()) }
    single { FeedRepository(get(), get()) }

    single { GetFeedUseCase(get()) }
    single { GetAllFeedsUseCase(get()) }
    single { CreateFeedUseCase(get()) }
    single { UpdateFeedUseCase(get()) }
    single { DeleteFeedUseCase(get(), get()) }
    single { DeleteAllFeedsUseCase(get(), get()) }
    single { GetFeedTypeUseCase(get(), get(), get()) }
}

internal val postSharedModule = module {
    includes(coreModule)
    includes(rssSharedModule)
    includes(atomSharedModule)
    includes(jsonFeedSharedModule)
    includes(feedSharedModule)

    single { PostDataSource(get()) }
    single { PostRepository(get(), get(), get()) }
    single { GetPostsUseCase(get(), get(), get(), get(), get()) }
    single { GetFilteredPostsUseCase(get()) }
    single { GetFiltersUseCase(get()) }
    single { GetPostUseCase(get()) }
    single { UpdatePostUseCase(get()) }
    single { DeleteAllPostsUseCase(get()) }
    single { GetPostMetadataFromHtmlUseCase(get(), get()) }
}

internal val backupSharedModule = module {
    includes(coreModule, feedSharedModule, categorySharedModule, postSharedModule, iconSharedModule)
    single { GetExportDataUseCase(get(), get(), get(), get()) }
    single { ParseBackupDataUseCase(get()) }
    single { ImportDataUseCase(get(), get(), get(), get(), get()) }
    single { ExportDataUseCase(get()) }
}

internal val settingsSharedModule = module {
    includes(coreModule)
    single { GetDefaultSettingsUseCase() }
    single { GetSettingsUseCase(get(), get()) }
    single { SavePreferenceUseCase(get()) }
}

internal val initializerSharedModule = module {
    includes(coreModule)
    includes(categorySharedModule)
    includes(feedSharedModule)

    single { CategoriesInitializer(get(), get(), get()) }
    single { MockFeedInitializer(get(), get()) }
    single { SyncPostsInitializer(get(), get(), get()) }
    single<AppInitializer> { AppInitializerImpl(get(), get(), get()) }
}

internal val backgroundTasksSharedModule = module {
    includes(coreModule)
    includes(postSharedModule)

    single<SyncPostsWorker> { SyncPostsWorkerImpl(get()) }
}
