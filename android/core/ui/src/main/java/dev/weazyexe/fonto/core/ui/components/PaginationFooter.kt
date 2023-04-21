package dev.weazyexe.fonto.core.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.pagination.PaginationState
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview

@Composable
fun PaginationFooter(
    state: PaginationState,
    onRefresh: () -> Unit
) {
    Crossfade(state) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 128.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (it) {
                PaginationState.LOADING ->
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )

                PaginationState.ERROR -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(id = R.string.error_pagination),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        TextButton(onClick = onRefresh) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_refresh_24),
                                contentDescription = null,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Text(text = stringResource(id = R.string.error_pane_refresh))
                        }
                    }
                }

                PaginationState.PAGINATION_EXHAUST -> {
                    Text(
                        text = stringResource(id = R.string.error_pagination_exhausted),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                else -> {

                }
            }
        }
    }
}

@Preview
@Composable
private fun PaginationFooterLoadingPreview() = ThemedPreview {
    PaginationFooter(state = PaginationState.LOADING) {}
}

@Preview
@Composable
private fun PaginationFooterErrorPreview() = ThemedPreview {
    PaginationFooter(state = PaginationState.ERROR) {}
}

@Preview
@Composable
private fun PaginationFooterExhaustedPreview() = ThemedPreview {
    PaginationFooter(state = PaginationState.PAGINATION_EXHAUST) {}
}
