package dev.weazyexe.fonto.common.di

import dev.weazyexe.fonto.common.data.datasource.FeedDataSource
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.usecase.GetFeedUseCase
import dev.weazyexe.fonto.common.db.createDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal val feedModule = module {
    includes(androidModule)

    single { createDatabase(get()) }
    single { FeedDataSource(get()) }
    single { FeedRepository(get()) }
    single { GetFeedUseCase(get()) }
}

fun appModules(): List<Module> = listOf(feedModule)