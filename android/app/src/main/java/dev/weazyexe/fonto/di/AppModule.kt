package dev.weazyexe.fonto.di

import dev.weazyexe.fonto.ui.AppViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AppViewModel(get()) }
}