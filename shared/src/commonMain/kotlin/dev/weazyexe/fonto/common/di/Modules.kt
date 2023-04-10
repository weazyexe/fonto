package dev.weazyexe.fonto.common.di

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.db.createDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal val feedModule = module {
    single { createDatabase(get()) }
    single { FeedRepository(get()) }
}

fun appModules(): List<Module> = listOf(feedModule, androidModule)