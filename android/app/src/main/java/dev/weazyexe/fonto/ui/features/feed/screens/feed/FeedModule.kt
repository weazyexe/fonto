package dev.weazyexe.fonto.ui.features.feed.screens.feed

import dev.weazyexe.fonto.features.feed.FeedDependencies
import dev.weazyexe.fonto.features.feed.FeedDomainState
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val feedScreenModule = module {
    factory { FeedDomainState() }
    factory { FeedDependencies(get(), get(), get(), get()) }
    viewModel { FeedViewModel(get()) }
}