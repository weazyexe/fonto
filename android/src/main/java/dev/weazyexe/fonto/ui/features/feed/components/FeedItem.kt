package dev.weazyexe.fonto.ui.features.feed.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.ui.features.feed.preview.FeedViewStatePreview
import dev.weazyexe.fonto.ui.features.feed.viewstates.FeedViewState
import dev.weazyexe.fonto.ui.theme.ThemedPreview

@Composable
fun FeedItem(
    feed: FeedViewState,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onSelectClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .defaultMinSize(minHeight = 72.dp)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imageModifier = Modifier
            .padding(end = 16.dp)
            .size(36.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(10.dp)

        if (feed.icon != null) {
            Image(
                bitmap = feed.icon.asImageBitmap(),
                contentDescription = null,
                modifier = imageModifier
            )
        } else {
            Image(
                imageVector = Icons.Default.Feed,
                contentDescription = null,
                modifier = imageModifier,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface)
            )
        }

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
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedItemPreview() = ThemedPreview {
    FeedItem(
        feed = FeedViewStatePreview.default,
        onClick = { },
        onDeleteClick = { },
        onSelectClick = { },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun FeedItemNoIconPreview() = ThemedPreview {
    FeedItem(
        feed = FeedViewStatePreview.noIcon,
        onClick = { },
        onDeleteClick = { },
        onSelectClick = { },
        modifier = Modifier.fillMaxWidth()
    )
}