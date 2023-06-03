package dev.weazyexe.fonto.core.ui.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FontoTextButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        if (isLoading) {
            SmallProgressIndicator()
        } else {
            Text(text = title)
        }
    }
}