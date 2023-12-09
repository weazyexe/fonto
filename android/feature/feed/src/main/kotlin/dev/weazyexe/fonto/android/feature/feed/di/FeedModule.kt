package dev.weazyexe.fonto.android.feature.feed.di

import android.app.Application
import dev.weazyexe.fonto.android.feature.feed.screens.addeditcategory.AddEditCategoryViewModel
import dev.weazyexe.fonto.android.feature.feed.screens.addeditfeed.AddEditFeedViewModel
import dev.weazyexe.fonto.android.feature.feed.screens.categories.CategoriesViewModel
import dev.weazyexe.fonto.android.feature.feed.screens.feed.FeedViewModel
import dev.weazyexe.fonto.android.feature.feed.screens.feed.components.search.SearchViewModel
import dev.weazyexe.fonto.android.feature.feed.screens.managefeed.ManageFeedViewModel
import dev.weazyexe.fonto.feature.feed.di.FeedFeatureModules
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val feedModule = module {
    includes(FeedFeatureModules)

    viewModel { FeedViewModel(get()) }
    viewModel { SearchViewModel(get(), androidContext() as Application) }
    viewModel { ManageFeedViewModel(get()) }
    viewModel { AddEditFeedViewModel(get(), get()) }
    viewModel { CategoriesViewModel(get()) }
    viewModel { AddEditCategoryViewModel(get(), get()) }
}
