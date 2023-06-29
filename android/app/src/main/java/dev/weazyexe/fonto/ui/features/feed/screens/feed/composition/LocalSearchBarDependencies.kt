package dev.weazyexe.fonto.ui.features.feed.screens.feed.composition

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import dev.weazyexe.fonto.ui.features.home.CategoryPickerResult
import dev.weazyexe.fonto.ui.features.home.DateRangePickerResult
import dev.weazyexe.fonto.ui.features.home.FeedPickerResult

val LocalNavController: ProvidableCompositionLocal<NavController> =
    staticCompositionLocalOf { error("No NavController provided") }

val LocalDateRangePickerResult: ProvidableCompositionLocal<DateRangePickerResult> =
    staticCompositionLocalOf { error("No DateRangePickerResult provided") }

val LocalFeedPickerResult: ProvidableCompositionLocal<FeedPickerResult> =
    staticCompositionLocalOf { error("No FeedPickerResult provided") }

val LocalCategoryPickerResult: ProvidableCompositionLocal<CategoryPickerResult> =
    staticCompositionLocalOf { error("No CategoryPickerResults provided") }