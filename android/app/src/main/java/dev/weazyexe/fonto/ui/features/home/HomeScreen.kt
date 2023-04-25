package dev.weazyexe.fonto.ui.features.home

import androidx.compose.material3.ExperimentalMaterial3Api
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
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.ui.features.NavGraphs
import dev.weazyexe.fonto.ui.features.destinations.ManageFeedScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.ThemePickerDialogDestination
import dev.weazyexe.fonto.ui.features.feed.screens.feed.FeedViewModel
import dev.weazyexe.fonto.ui.features.home.bottombar.BottomBar
import dev.weazyexe.fonto.ui.features.home.dependencies.NavigateTo
import dev.weazyexe.fonto.ui.features.home.dependencies.NavigateWithResult
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navController: NavController,
    manageFeedResultRecipient: ResultRecipient<ManageFeedScreenDestination, Boolean>,
    themePickerResultRecepient: ResultRecipient<ThemePickerDialogDestination, Theme?>
) {
    val feedViewModel = koinViewModel<FeedViewModel>()
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
                dependency(
                    fun(): ResultRecipient<ManageFeedScreenDestination, Boolean> {
                        return manageFeedResultRecipient
                    }
                )
                dependency(
                    fun(): ResultRecipient<ThemePickerDialogDestination, Theme?> {
                        return themePickerResultRecepient
                    }
                )
                dependency(
                    object : NavigateTo {
                        override fun invoke(direction: Direction) {
                            navController.navigate(direction)
                        }
                    }
                )
                dependency(
                    object : NavigateWithResult {
                        override fun invoke(direction: Direction) {
                            navController.navigate(direction)
                        }
                    }
                )
            }
        )
    }
}
