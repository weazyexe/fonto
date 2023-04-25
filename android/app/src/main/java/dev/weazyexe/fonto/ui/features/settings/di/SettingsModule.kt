package dev.weazyexe.fonto.ui.features.settings.di

import dev.weazyexe.fonto.ui.features.settings.screens.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel { SettingsViewModel(get(), get()) }
}