@file:JvmName("CommonModules")
package dev.weazyexe.fonto.common.di

import dev.weazyexe.fonto.common.data.datasource.FeedDataSource
import dev.weazyexe.fonto.common.data.datasource.IconDataSource
import dev.weazyexe.fonto.common.data.datasource.NewslineDataSource
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.IconRepository
import dev.weazyexe.fonto.common.data.repository.NewslineRepository
import dev.weazyexe.fonto.common.data.usecase.GetIconByRssUrlUseCase
import dev.weazyexe.fonto.common.data.usecase.IsNewslineValidUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.CreateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.DeleteFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.UpdateFeedUseCase
import dev.weazyexe.fonto.common.db.createDatabase
import dev.weazyexe.fonto.common.network.createHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

internal val coreModule = module {
    includes(androidModule)
    single { createDatabase(get()) }
    single { createHttpClient() }
}

internal val feedModule = module {
    includes(coreModule)

    single { FeedDataSource(get()) }
    single { FeedRepository(get()) }
    single { GetFeedUseCase(get()) }
    single { CreateFeedUseCase(get()) }
    single { UpdateFeedUseCase(get()) }
    single { DeleteFeedUseCase(get()) }
}

internal val iconModule = module {
    includes(coreModule)

    single { IconDataSource(get()) }
    single { IconRepository(get()) }
    single { GetIconByRssUrlUseCase(get()) }
}

internal val newslineModule = module {
    includes(coreModule)

    single { NewslineDataSource() }
    single { NewslineRepository(get()) }
    single { IsNewslineValidUseCase(get()) }
}

fun appModules(): List<Module> = listOf(feedModule, iconModule, newslineModule)