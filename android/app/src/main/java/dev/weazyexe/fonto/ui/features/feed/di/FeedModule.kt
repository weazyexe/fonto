package dev.weazyexe.fonto.ui.features.feed.di

import dev.weazyexe.fonto.app.FontoApplication
import dev.weazyexe.fonto.ui.features.feed.screens.addeditcategory.AddEditCategoryViewModel
import dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed.AddEditFeedViewModel
import dev.weazyexe.fonto.ui.features.feed.screens.categories.CategoriesViewModel
import dev.weazyexe.fonto.ui.features.feed.screens.feed.FeedViewModel
import dev.weazyexe.fonto.ui.features.feed.screens.feed.components.search.SearchViewModel
import dev.weazyexe.fonto.ui.features.feed.screens.managefeed.ManageFeedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val feedModule = module {
    viewModel { FeedViewModel(get()) }
    viewModel { SearchViewModel(get(), androidContext() as FontoApplication) }
    viewModel { ManageFeedViewModel(get()) }
    viewModel { AddEditFeedViewModel(get(), get()) }
    viewModel { CategoriesViewModel(get()) }
    viewModel { AddEditCategoryViewModel(get(), get()) }
}
