package dev.weazyexe.fonto.ui.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.dependency
import dev.weazyexe.fonto.ui.screens.HomeNavGraph
import dev.weazyexe.fonto.ui.screens.NavGraphs
import dev.weazyexe.fonto.ui.screens.home.bottombar.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@HomeNavGraph(start = true)
@Destination
@Composable
fun HomeScreen() {
    val bottomBarNavController = rememberNavController()
    Scaffold(
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