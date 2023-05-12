@file:JvmName("CommonModules")

package dev.weazyexe.fonto.common.di

import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.data.datasource.AtomDataSource
import dev.weazyexe.fonto.common.data.datasource.FeedDataSource
import dev.weazyexe.fonto.common.data.datasource.IconDataSource
import dev.weazyexe.fonto.common.data.datasource.NewslineDataSource
import dev.weazyexe.fonto.common.data.datasource.RssDataSource
import dev.weazyexe.fonto.common.data.repository.AtomRepository
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.IconRepository
import dev.weazyexe.fonto.common.data.repository.NewslineRepository
import dev.weazyexe.fonto.common.data.repository.RssRepository
import dev.weazyexe.fonto.common.data.usecase.atom.IsAtomValidUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.CreateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.DeleteAllFeedsUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.DeleteFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedIconUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedTypeUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.UpdateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.icon.GetIconByRssUrlUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetNewslineUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetPaginatedNewslineUseCase
import dev.weazyexe.fonto.common.data.usecase.newsline.GetPostUseCase
import dev.weazyexe.fonto.common.data.usecase.rss.IsRssValidUseCase
import dev.weazyexe.fonto.common.db.createDatabase
import dev.weazyexe.fonto.common.feature.parser.atom.AtomParser
import dev.weazyexe.fonto.common.feature.parser.rss.RssParser
import dev.weazyexe.fonto.common.feature.settings.createSettingsStorage
import dev.weazyexe.fonto.common.network.createHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformModule(): Module

internal val coreModule = module {
    includes(platformModule())

    single { createDatabase(get()) }
    single { createHttpClient() }
    single { createSettingsStorage(get()) }

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

internal val feedModule = module {
    includes(coreModule)
    includes(rssModule)
    includes(atomModule)

    single { FeedDataSource(get()) }
    single { FeedRepository(get()) }

    single { GetFeedUseCase(get()) }
    single { CreateFeedUseCase(get()) }
    single { UpdateFeedUseCase(get()) }
    single { DeleteFeedUseCase(get(), get()) }
    single { DeleteAllFeedsUseCase(get(), get()) }
    single { GetFeedIconUseCase(get()) }
    single { GetFeedTypeUseCase(get(), get()) }
}

internal val newslineModule = module {
    includes(coreModule)
    includes(rssModule)
    includes(atomModule)
    includes(feedModule)

    single { NewslineDataSource(get()) }
    single { NewslineRepository(get()) }
    single { GetNewslineUseCase(get(), get(), get()) }
    single { GetPaginatedNewslineUseCase(get()) }
    single { GetPostUseCase(get(), get()) }
}

fun appModules(): List<Module> =
    listOf(feedModule, iconModule, rssModule, atomModule, newslineModule)
