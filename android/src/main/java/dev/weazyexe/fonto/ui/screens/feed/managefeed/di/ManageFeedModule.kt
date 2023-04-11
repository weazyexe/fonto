package dev.weazyexe.fonto.ui.screens.feed.managefeed.di

import dev.weazyexe.fonto.ui.screens.feed.managefeed.ManageFeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val manageFeedModule = module {
    viewModel { ManageFeedViewModel(get()) }
}