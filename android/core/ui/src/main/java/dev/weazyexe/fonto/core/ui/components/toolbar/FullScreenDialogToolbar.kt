package dev.weazyexe.fonto.core.ui.components.toolbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.components.CloseDialogButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenDialogToolbar(
    title: String,
    doneButtonText: String,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = { CloseDialogButton(onBackClick) },
        actions = {
            TextButton(onClick = onDoneClick) {
                Text(text = doneButtonText)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                elevation = 3.dp
            )
        )
    )
}