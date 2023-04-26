package dev.weazyexe.fonto.core.ui.theme

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ThemedPreview(content: @Composable () -> Unit) {
    FontoTheme(accentColor = Color(DEFAULT_COLOR.data)) {
        Surface {
            content()
        }
    }
}