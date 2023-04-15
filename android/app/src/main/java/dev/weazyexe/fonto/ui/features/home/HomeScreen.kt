package dev.weazyexe.fonto.ui.features.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import dev.weazyexe.fonto.R
import dev.weazyexe.fonto.core.ui.components.AnimatedAppearing
import dev.weazyexe.fonto.ui.features.NavGraphs
import dev.weazyexe.fonto.ui.features.destinations.ManageFeedScreenDestination
import dev.weazyexe.fonto.ui.features.feed.screens.feed.FeedViewModel
import dev.weazyexe.fonto.ui.features.home.bottombar.BottomBar
import dev.weazyexe.fonto.ui.navigation.HomeNavGraph
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@HomeNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navController: NavController
) {
    val feedViewModel = koinViewModel<FeedViewModel>()
    val bottomBarNavController = rememberNavController()

    Scaffold(
        floatingActionButton = {
            AnimatedAppearing {
                FloatingActionButton(
                    onClick = { navController.navigate(ManageFeedScreenDestination) }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_24),
                        contentDescription = null
                    )
                }
            }
        },
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
                    fun(destination: DirectionDestinationSpec) {
                        navController.navigate(destination)
                    }
                )
            }
        )
    }
}