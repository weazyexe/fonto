package dev.weazyexe.fonto.ui.features.home

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.Direction
import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.ui.features.NavGraphs
import dev.weazyexe.fonto.ui.features.destinations.CategoryPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.ColorPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.DateRangePickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.ExportStrategyPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.FeedPickerDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.ManageFeedScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.ThemePickerDialogDestination
import dev.weazyexe.fonto.ui.features.feed.screens.categorypicker.CategoryPickerResult
import dev.weazyexe.fonto.ui.features.feed.screens.daterangepicker.DateRangeResults
import dev.weazyexe.fonto.ui.features.feed.screens.feed.FeedViewModel
import dev.weazyexe.fonto.ui.features.feed.screens.feedpicker.FeedPickerResult
import dev.weazyexe.fonto.ui.features.home.bottombar.BottomBar
import dev.weazyexe.fonto.ui.features.home.dependencies.CategoryPickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.ColorPickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.DateRangePickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.ExportStrategyPickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.FeedPickerResults
import dev.weazyexe.fonto.ui.features.home.dependencies.ManageFeedResults
import dev.weazyexe.fonto.ui.features.home.dependencies.NavigateTo
import dev.weazyexe.fonto.ui.features.home.dependencies.ThemePickerResults
import dev.weazyexe.fonto.ui.features.settings.screens.exportstrategypicker.ExportStrategyResults
import dev.weazyexe.fonto.ui.features.settings.screens.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navController: NavController,
    manageFeedResultRecipient: ResultRecipient<ManageFeedScreenDestination, Boolean>,
    themePickerResultRecepient: ResultRecipient<ThemePickerDialogDestination, Theme?>,
    colorPickerResultRecipient: ResultRecipient<ColorPickerDialogDestination, ColorScheme>,
    dateRangePickerResultRecipient: ResultRecipient<DateRangePickerDialogDestination, DateRangeResults?>,
    feedPickerResultRecipient: ResultRecipient<FeedPickerDialogDestination, FeedPickerResult?>,
    categoryPickerResultRecipient: ResultRecipient<CategoryPickerDialogDestination, CategoryPickerResult?>,
    exportStrategyPickerResultsRecipient: ResultRecipient<ExportStrategyPickerDialogDestination, ExportStrategyResults?>
) {
    val feedViewModel = koinViewModel<FeedViewModel>()
    val settingsViewModel = koinViewModel<SettingsViewModel>()
    val bottomBarNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = bottomBarNavController)
        }
    ) { padding ->
        DestinationsNavHost(
            navGraph = NavGraphs.bottomBar,
            navController = bottomBarNavController,
            dependenciesContainerBuilder = {
                dependency(padding)
                dependency(feedViewModel)
                dependency(settingsViewModel)
                dependency(
                    object : ManageFeedResults {
                        override fun invoke(): ResultRecipient<ManageFeedScreenDestination, Boolean> {
                            return manageFeedResultRecipient
                        }
                    }
                )
                dependency(
                    object : DateRangePickerResults {
                        override fun invoke(): ResultRecipient<DateRangePickerDialogDestination, DateRangeResults?> {
                            return dateRangePickerResultRecipient
                        }
                    }
                )
                dependency(
                    object : FeedPickerResults {
                        override fun invoke(): ResultRecipient<FeedPickerDialogDestination, FeedPickerResult?> {
                            return feedPickerResultRecipient
                        }
                    }
                )
                dependency(
                    object : CategoryPickerResults {
                        override fun invoke(): ResultRecipient<CategoryPickerDialogDestination, CategoryPickerResult?> {
                            return categoryPickerResultRecipient
                        }
                    }
                )
                dependency(
                    object : ThemePickerResults {
                        override fun invoke(): ResultRecipient<ThemePickerDialogDestination, Theme?> {
                            return themePickerResultRecepient
                        }
                    }
                )
                dependency(
                    object : ColorPickerResults {
                        override fun invoke(): ResultRecipient<ColorPickerDialogDestination, ColorScheme> {
                            return colorPickerResultRecipient
                        }
                    }
                )
                dependency(
                    object : ExportStrategyPickerResults {
                        override fun invoke(): ResultRecipient<ExportStrategyPickerDialogDestination, ExportStrategyResults?> {
                            return exportStrategyPickerResultsRecipient
                        }
                    }
                )
                dependency(
                    object : NavigateTo {
                        override fun invoke(direction: Direction) {
                            navController.navigate(direction)
                        }
                    }
                )
            }
        )
    }
}
