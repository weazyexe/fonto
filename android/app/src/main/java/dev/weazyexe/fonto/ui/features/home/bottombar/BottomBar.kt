package dev.weazyexe.fonto.ui.features.home.bottombar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import dev.weazyexe.fonto.ui.features.NavGraphs
import dev.weazyexe.fonto.ui.features.appCurrentDestinationAsState
import dev.weazyexe.fonto.ui.features.destinations.AppDestination
import dev.weazyexe.fonto.ui.features.startAppDestination

@Composable
fun BottomBar(navController: NavController) {
    val currentDestination: AppDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.bottomBar.startAppDestination

    NavigationBar {
        BottomBarDestination.values().forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigate(destination.direction.route) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.icon),
                        contentDescription = stringResource(destination.label)
                    )
                },
                label = { Text(stringResource(destination.label)) },
            )
        }
    }
}
