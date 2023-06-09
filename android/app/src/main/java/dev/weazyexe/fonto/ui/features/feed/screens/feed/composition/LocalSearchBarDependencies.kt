package dev.weazyexe.fonto.ui.features.feed.screens.feed.composition

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import dev.weazyexe.fonto.ui.features.home.dependencies.CategoryPickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.DateRangePickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.FeedPickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.NavigateTo

val LocalNavigateTo: ProvidableCompositionLocal<NavigateTo> =
    staticCompositionLocalOf { error("No NavigateTo provided") }

val LocalDateRangePickerResults: ProvidableCompositionLocal<DateRangePickerResults> =
    staticCompositionLocalOf { error("No DateRangePickerResults provided") }

val LocalFeedPickerResults: ProvidableCompositionLocal<FeedPickerResults> =
    staticCompositionLocalOf { error("No FeedPickerResults provided") }

val LocalCategoryPickerResults: ProvidableCompositionLocal<CategoryPickerResults> =
    staticCompositionLocalOf { error("No CategoryPickerResults provided") }