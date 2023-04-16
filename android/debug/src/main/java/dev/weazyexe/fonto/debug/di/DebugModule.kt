package dev.weazyexe.fonto.debug.di

import dev.weazyexe.fonto.debug.DebugViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val debugModule = module {
    viewModel { DebugViewModel(get(), get()) }
}