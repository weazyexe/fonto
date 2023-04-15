package dev.weazyexe.fonto.core.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.core.ui.R

@Composable
fun CloseDialogButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close_24),
            contentDescription = stringResource(id = R.string.accessibility_close_dialog)
        )
    }
}