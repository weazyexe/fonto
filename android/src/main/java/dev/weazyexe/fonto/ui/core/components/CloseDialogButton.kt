package dev.weazyexe.fonto.ui.core.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.R

@Composable
fun CloseDialogButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(id = R.string.accessibility_close_dialog)
        )
    }
}