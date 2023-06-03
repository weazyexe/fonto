package dev.weazyexe.fonto.core.ui.components.preferences

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AlertDialogPicker(
    title: String,
    saveButtonLabel: String,
    cancelButtonLabel: String,
    @DrawableRes icon: Int,
    isSaveButtonEnabled: Boolean = true,
    hasCancelButton: Boolean = true,
    onCancel: () -> Unit,
    onSave: () -> Unit,
    beforeDividerContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = onSave, enabled = isSaveButtonEnabled) {
                Text(saveButtonLabel)
            }
        },
        dismissButton = {
            if (hasCancelButton) {
                TextButton(onClick = onCancel) {
                    Text(text = cancelButtonLabel)
                }
            }
        },
        title = { Text(text = title) },
        text = {
            Column(modifier = Modifier.heightIn(max = 384.dp)) {
                beforeDividerContent()

                Divider(color = MaterialTheme.colorScheme.outlineVariant)

                Box(modifier = Modifier.weight(1f, fill = false)) {
                    content()
                }

                Divider(color = MaterialTheme.colorScheme.outlineVariant)
            }
        },
        icon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }
    )
}