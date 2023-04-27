package dev.weazyexe.fonto.core.ui.theme

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

@Composable
fun ThemedPreview(content: @Composable () -> Unit) {
    FontoTheme(accentColor = DEFAULT_COLOR.data) {
        Surface {
            content()
        }
    }
}