package dev.weazyexe.fonto.app.di

import dev.weazyexe.fonto.data.SettingsStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { SettingsStorage(androidContext()) }
}