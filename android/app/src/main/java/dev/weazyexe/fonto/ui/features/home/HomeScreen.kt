package dev.weazyexe.fonto.ui.features.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.ui.features.bookmarks.BookmarksScreen
import dev.weazyexe.fonto.ui.features.destinations.CategoryPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.ColorPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.DateRangePickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.ExportStrategyPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.FeedPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.ThemePickerDialogDestination
import dev.weazyexe.fonto.ui.features.feed.screens.categorypicker.CategoryPickerResults
import dev.weazyexe.fonto.ui.features.feed.screens.daterangepicker.DateRangeResults
import dev.weazyexe.fonto.ui.features.feed.screens.feed.FeedScreen
import dev.weazyexe.fonto.ui.features.feed.screens.feed.FeedViewModel
import dev.weazyexe.fonto.ui.features.feed.screens.feedpicker.FeedPickerResults
import dev.weazyexe.fonto.ui.features.home.bottombar.BottomBar
import dev.weazyexe.fonto.ui.features.home.bottombar.BottomBarDestination
import dev.weazyexe.fonto.ui.features.settings.screens.exportstrategypicker.ExportStrategyResults
import dev.weazyexe.fonto.ui.features.settings.screens.settings.SettingsScreen
import dev.weazyexe.fonto.ui.features.settings.screens.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

typealias DateRangePickerResult = ResultRecipient<DateRangePickerDialogDestination, DateRangeResults?>
typealias FeedPickerResult = ResultRecipient<FeedPickerDialogDestination, FeedPickerResults?>
typealias CategoryPickerResult = ResultRecipient<CategoryPickerDialogDestination, CategoryPickerResults?>
typealias ThemePickerResult = ResultRecipient<ThemePickerDialogDestination, Theme?>
typealias ColorPickerResult = ResultRecipient<ColorPickerDialogDestination, ColorScheme>
typealias ExportStrategyPickerResult = ResultRecipient<ExportStrategyPickerDialogDestination, ExportStrategyResults?>

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navController: NavController,
    themePickerResultRecipient: ResultRecipient<ThemePickerDialogDestination, Theme?>,
    colorPickerResultRecipient: ResultRecipient<ColorPickerDialogDestination, ColorScheme>,
    dateRangePickerResultRecipient: ResultRecipient<DateRangePickerDialogDestination, DateRangeResults?>,
    feedPickerResultRecipient: ResultRecipient<FeedPickerDialogDestination, FeedPickerResults?>,
    categoryPickerResultsRecipient: ResultRecipient<CategoryPickerDialogDestination, CategoryPickerResults?>,
    exportStrategyPickerResultsRecipient: ResultRecipient<ExportStrategyPickerDialogDestination, ExportStrategyResults?>
) {
    val feedViewModel = koinViewModel<FeedViewModel>()
    val settingsViewModel = koinViewModel<SettingsViewModel>()

    var currentDestination by rememberSaveable { mutableStateOf(BottomBarDestination.Feed) }

    Scaffold(
        bottomBar = {
            BottomBar(
                currentDestination = currentDestination,
                onTabChanged = { currentDestination = it }
            )
        }
    ) { padding ->
        Crossfade(
            targetState = currentDestination,
            label = "bottom_bar_crossfade"
        ) {
            AnimatedVisibility(visible = it == BottomBarDestination.Feed) {
                FeedScreen(
                    rootPaddingValues = padding,
                    viewModel = feedViewModel,
                    navController = navController,
                    dateRangePickerResult = dateRangePickerResultRecipient,
                    feedPickerResult = feedPickerResultRecipient,
                    categoryPickerResults = categoryPickerResultsRecipient
                )
            }

            AnimatedVisibility(visible = it == BottomBarDestination.Bookmarks) {
                BookmarksScreen(onBack = { currentDestination = BottomBarDestination.Feed })
            }

            AnimatedVisibility(visible = it == BottomBarDestination.Settings) {
                SettingsScreen(
                    rootPaddingValues = padding,
                    viewModel = settingsViewModel,
                    navController = navController,
                    themePickerResult = themePickerResultRecipient,
                    colorPickerResult = colorPickerResultRecipient,
                    exportStrategyPickerResults = exportStrategyPickerResultsRecipient,
                    onBack = { currentDestination = BottomBarDestination.Feed }
                )
            }
            /*when (it) {
                BottomBarDestination.Feed -> {
                    FeedScreen(
                        rootPaddingValues = padding,
                        viewModel = feedViewModel,
                        navController = navController,
                        dateRangePickerResult = dateRangePickerResultRecipient,
                        feedPickerResult = feedPickerResultRecipient,
                        categoryPickerResults = categoryPickerResultsRecipient
                    )
                }

                BottomBarDestination.Bookmarks -> {
                    BookmarksScreen(onBack = { currentDestination = BottomBarDestination.Feed })
                }

                BottomBarDestination.Settings -> {
                    SettingsScreen(
                        rootPaddingValues = padding,
                        viewModel = settingsViewModel,
                        navController = navController,
                        themePickerResult = themePickerResultRecipient,
                        colorPickerResult = colorPickerResultRecipient,
                        exportStrategyPickerResults = exportStrategyPickerResultsRecipient,
                        onBack = { currentDestination = BottomBarDestination.Feed }
                    )
                }
            }*/
        }
    }
}
