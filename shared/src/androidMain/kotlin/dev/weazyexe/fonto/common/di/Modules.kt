package dev.weazyexe.fonto.common.di

import dev.weazyexe.fonto.common.db.DriverFactory
import dev.weazyexe.fonto.common.feature.settings.SettingsStorageFactory
import org.koin.dsl.module

actual fun platformModule() = module {
    single { DriverFactory(get()) }
    single { SettingsStorageFactory(get()) }
}
