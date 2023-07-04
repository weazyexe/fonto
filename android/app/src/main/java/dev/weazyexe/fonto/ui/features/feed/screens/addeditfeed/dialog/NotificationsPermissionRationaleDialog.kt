package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.ui.navigation.ExternalRouter

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun NotificationsPermissionRationaleDialog(
    navController: NavController
) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { navController.navigateUp() },
        confirmButton = {
            TextButton(onClick = { navController.navigateUp() }) {
                Text(text = stringResource(id = StringResources.permission_rationale_notification_ok))
            }
        },
        dismissButton = {
            TextButton(onClick = { ExternalRouter.openNotificationsSettings(context) }) {
                Text(text = stringResource(id = StringResources.permission_rationale_notification_open_settings))
            }
        },
        icon = {
            Icon(
                painter = painterResource(id = DrawableResources.ic_notification_24),
                contentDescription = null
            )
        },
        title = {
            Text(text = stringResource(id = StringResources.permission_rationale_notification_title))
        },
        text = {
            Text(text = stringResource(id = StringResources.permission_rationale_notification_description))
        }
    )
}