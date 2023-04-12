package dev.weazyexe.fonto.common.di

import dev.weazyexe.fonto.common.data.datasource.FeedDataSource
import dev.weazyexe.fonto.common.data.datasource.IconDataSource
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.IconRepository
import dev.weazyexe.fonto.common.data.usecase.CreateFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.GetFeedUseCase
import dev.weazyexe.fonto.common.data.usecase.GetIconByRssUrlUseCase
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
}

internal val iconModule = module {
    includes(coreModule)

    single { IconDataSource(get()) }
    single { IconRepository(get()) }
    single { GetIconByRssUrlUseCase(get()) }
}

fun appModules(): List<Module> = listOf(feedModule, iconModule)