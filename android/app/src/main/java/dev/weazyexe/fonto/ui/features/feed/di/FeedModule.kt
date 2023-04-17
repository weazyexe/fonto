package dev.weazyexe.fonto.ui.features.feed.di

import dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed.AddEditFeedViewModel
import dev.weazyexe.fonto.ui.features.feed.screens.feed.FeedViewModel
import dev.weazyexe.fonto.ui.features.feed.screens.managefeed.ManageFeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val feedModule = module {
    viewModel { FeedViewModel(get(), get()) }
    viewModel { ManageFeedViewModel(get(), get()) }
    viewModel { AddEditFeedViewModel(get(), get(), get(), get(), get(), get()) }
}