package dev.weazyexe.fonto.ui.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate
import dev.weazyexe.fonto.ui.screens.HomeNavGraph
import dev.weazyexe.fonto.ui.screens.NavGraphs
import dev.weazyexe.fonto.ui.screens.destinations.ManageFeedScreenDestination
import dev.weazyexe.fonto.ui.screens.home.bottombar.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@HomeNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navController: NavController
) {
    val bottomBarNavController = rememberNavController()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(ManageFeedScreenDestination) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
        bottomBar = {
            BottomBar(navController = bottomBarNavController)
        }
    ) { padding ->
        DestinationsNavHost(
            navGraph = NavGraphs.bottomBar,
            navController = bottomBarNavController,
            dependenciesContainerBuilder = { dependency(padding) }
        )
    }
}