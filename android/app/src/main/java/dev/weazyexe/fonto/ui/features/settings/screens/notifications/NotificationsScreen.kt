package dev.weazyexe.fonto.ui.features.settings.screens.notifications

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun NotificationsScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<NotificationsViewModel>()
    val state by viewModel.state.collectAsState()

    NotificationsBody(
        feeds = state.feeds,
        onFeedClick = {},
        onBackClick = { navController.navigateUp() }
    )
}