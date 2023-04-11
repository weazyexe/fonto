package dev.weazyexe.fonto.ui.screens.feed.managefeed.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.ui.screens.feed.managefeed.viewstates.FeedViewState

@Composable
fun FeedItem(
    feed: FeedViewState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row {
            Text(text = feed.title)

            if (feed.icon != null) {
                Image(
                    bitmap = feed.icon.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(start = 16.dp, end = 4.dp)
                )
            }
        }

        Text(text = feed.link)
    }
}