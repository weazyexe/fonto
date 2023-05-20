package dev.weazyexe.fonto.core.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import dev.weazyexe.fonto.core.ui.utils.DrawableResources

@Composable
fun DeleteConfirmationDialog(
    title: String,
    description: AnnotatedString,
    deleteButtonText: String,
    cancelButtonText: String,
    onDeleteClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancelClick,
        confirmButton = {
            FilledTonalButton(
                onClick = onDeleteClick,
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text(text = deleteButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelClick) {
                Text(text = cancelButtonText)
            }
        },
        title = { Text(text = title) },
        text = {
            Text(text = description)
        },
        icon = {
            Icon(
                painter = painterResource(id = DrawableResources.ic_delete_forever_24),
                contentDescription = null
            )
        }
    )
}