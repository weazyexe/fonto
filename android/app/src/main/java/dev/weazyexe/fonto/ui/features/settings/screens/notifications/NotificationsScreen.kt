package dev.weazyexe.fonto.ui.features.settings.screens.notifications

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.ui.features.destinations.NotificationsPermissionRationaleDialogDestination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun NotificationsScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<NotificationsViewModel>()
    val state by viewModel.state.collectAsState()

    var lastToggledFeedId: Feed.Id? by remember { mutableStateOf(null) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isPermissionGranted ->
            if (isPermissionGranted) {
                lastToggledFeedId?.let {
                    viewModel.toggleNotificationsEnabled(it)
                    lastToggledFeedId = null
                }
            } else {
                navController.navigate(NotificationsPermissionRationaleDialogDestination)
            }
        }
    )

    NotificationsBody(
        feeds = state.feeds,
        onFeedClick = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                lastToggledFeedId = it
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                viewModel.toggleNotificationsEnabled(it)
            }
        },
        onBackClick = { navController.navigateUp() }
    )
}