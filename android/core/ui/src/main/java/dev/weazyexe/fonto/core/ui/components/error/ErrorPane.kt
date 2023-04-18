package dev.weazyexe.fonto.core.ui.components.error

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview

@Composable
fun ErrorPane(params: ErrorPaneParams) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // base color #9A9A9A
        Text(
            text = params.emoji,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center
        )

        Text(
            text = params.title,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Text(
            text = params.message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        if (params.action != null) {
            Spacer(modifier = Modifier.size(16.dp))

            FilledTonalButton(onClick = params.action.onClick) {
                Text(
                    text = stringResource(params.action.title),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Preview(showBackground = true, wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE)
@Composable
private fun ErrorPanePreview() = ThemedPreview {
    ErrorPane(params = ErrorPaneParams.unknown())
}

@Preview(showBackground = true, wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE)
@Composable
private fun ErrorPaneNoInternetPreview() = ThemedPreview {
    ErrorPane(
        params = ErrorPaneParams.noInternet(
            action = ErrorPaneParams.Action(
                title = R.string.error_pane_refresh,
                onClick = {}
            )
        )
    )
}

@Preview(
    showBackground = true,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun ErrorPaneEmptyPreview() = ThemedPreview {
    ErrorPane(
        params = ErrorPaneParams.empty(
            action = ErrorPaneParams.Action(
                title = R.string.error_pane_refresh,
                onClick = {}
            )
        )
    )
}