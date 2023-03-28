package dev.weazyexe.fonto.ui.theme

import androidx.compose.runtime.Composable

@Composable
fun ThemedPreview(content: @Composable () -> Unit) {
    FontoTheme {
        content()
    }
}