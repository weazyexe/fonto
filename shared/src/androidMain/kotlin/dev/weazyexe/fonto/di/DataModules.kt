package dev.weazyexe.fonto.di

import dev.weazyexe.fonto.common.db.DriverFactory
import dev.weazyexe.fonto.common.feature.settings.SettingsStorageFactory
import dev.weazyexe.fonto.common.html.OgMetadataExtractorFactory
import dev.weazyexe.fonto.common.resources.StringsProviderFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun platformModule() = module {
    single { DriverFactory(androidContext()) }
    single { SettingsStorageFactory(androidContext()) }
    single { StringsProviderFactory(androidContext()) }
    single { OgMetadataExtractorFactory() }
}
