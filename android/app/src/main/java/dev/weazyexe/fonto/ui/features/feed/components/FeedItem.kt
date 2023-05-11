package dev.weazyexe.fonto.ui.features.feed.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.R
import dev.weazyexe.fonto.ui.features.feed.preview.FeedViewStatePreview
import dev.weazyexe.fonto.ui.features.feed.viewstates.FeedViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FeedItem(
    feed: FeedViewState,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var isActionsDropdownExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .defaultMinSize(minHeight = 72.dp)
            .clickable(onClick = onClick)
            .padding(start = 16.dp, top = 16.dp, end = 8.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FeedIcon(
            icon = feed.icon?.asImageBitmap(),
            modifier = Modifier.padding(end = 16.dp)
        )

        Column {
            Text(
                text = feed.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = feed.link,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.weight(weight = 1f))
        
        IconButton(onClick = { isActionsDropdownExpanded = true }) {
            DropdownMenu(
                expanded = isActionsDropdownExpanded,
                onDismissRequest = { isActionsDropdownExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.add_edit_feed_delete)) },
                    onClick = {
                        onDeleteClick()
                        scope.launch {
                            delay(100)
                            isActionsDropdownExpanded = false
                        }
                    }
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_more_horizontal_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedItemPreview() = dev.weazyexe.fonto.core.ui.theme.ThemedPreview {
    FeedItem(
        feed = FeedViewStatePreview.default,
        onClick = { },
        onDeleteClick = { },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun FeedItemNoIconPreview() = dev.weazyexe.fonto.core.ui.theme.ThemedPreview {
    FeedItem(
        feed = FeedViewStatePreview.noIcon,
        onClick = { },
        onDeleteClick = { },
        modifier = Modifier.fillMaxWidth()
    )
}