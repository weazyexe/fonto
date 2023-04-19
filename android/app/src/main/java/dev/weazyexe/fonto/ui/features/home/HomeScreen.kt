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
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import dev.weazyexe.fonto.ui.features.NavGraphs
import dev.weazyexe.fonto.ui.features.destinations.ManageFeedScreenDestination
import dev.weazyexe.fonto.ui.features.feed.screens.feed.FeedViewModel
import dev.weazyexe.fonto.ui.features.home.bottombar.BottomBar
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navController: NavController,
    manageFeedResultRecipient: ResultRecipient<ManageFeedScreenDestination, Boolean>
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
                    fun(destination: DirectionDestinationSpec) {
                        navController.navigate(destination)
                    }
                )
            }
        )
    }
}