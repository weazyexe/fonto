package dev.weazyexe.fonto.ui.features.settings.di

import dev.weazyexe.fonto.app.App
import dev.weazyexe.fonto.debug.DebugViewModel
import dev.weazyexe.fonto.ui.features.settings.screens.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel { SettingsViewModel(get(), androidContext() as App) }
    viewModel { DebugViewModel(get()) }
}
