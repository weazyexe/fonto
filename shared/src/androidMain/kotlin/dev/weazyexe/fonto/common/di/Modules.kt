package dev.weazyexe.fonto.common.di

import dev.weazyexe.fonto.common.db.DriverFactory
import org.koin.dsl.module

internal val androidModule = module {
    single { DriverFactory(get()) }
}
