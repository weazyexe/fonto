package dev.weazyexe.fonto.ui.features.feed.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.weazyexe.fonto.common.core.asBitmap
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview
import dev.weazyexe.fonto.core.ui.utils.formatHumanFriendly
import dev.weazyexe.fonto.ui.features.feed.preview.PostViewStatePreview
import dev.weazyexe.fonto.ui.features.feed.viewstates.PostViewState

@Composable
fun PostItem(
    post: PostViewState,
    onPostClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
            .clickable(onClick = onPostClick)
    ) {
        PostTitle(
            title = post.feed.title,
            publishedAt = post.publishedAt.formatHumanFriendly(),
            icon = post.feed.icon?.asBitmap()?.asImageBitmap(),
            isSaved = post.isSaved,
            onSaveClick = onSaveClick,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 8.dp, bottom = 8.dp)
                .fillMaxWidth()
        )

        PostBody(
            title = post.title,
            content = post.description
        )

        PostImage(imageUrl = post.imageUrl)
    }
}

@Composable
private fun ColumnScope.PostTitle(
    title: String,
    publishedAt: String,
    icon: ImageBitmap?,
    isSaved: Boolean,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FeedIcon(
            icon = icon,
            modifier = Modifier.padding(end = 16.dp)
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = publishedAt,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        IconButton(
            onClick = onSaveClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = painterResource(
                    id = if (isSaved) {
                        R.drawable.ic_bookmark_added_24
                    } else {
                        R.drawable.ic_bookmark_24
                    }
                ),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ColumnScope.PostBody(
    title: String,
    content: String
) {
    if (title.isNotBlank()) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )
    }

    Text(
        text = content,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 15,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ColumnScope.PostImage(imageUrl: String?) {
    if (imageUrl != null) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .heightIn(max = 384.dp)
                .fillMaxWidth()
                .padding(top = 12.dp),
            contentScale = ContentScale.FillWidth
        )
    } else {
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Preview
@Composable
private fun PostItemDefaultPreview() {
    ThemedPreview {
        PostItem(
            post = PostViewStatePreview.default,
            onPostClick = {},
            onSaveClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun PostItemNoPicturesPreview() {
    ThemedPreview {
        PostItem(
            post = PostViewStatePreview.noPictures,
            onPostClick = {},
            onSaveClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun PostItemSavedPreview() {
    ThemedPreview {
        PostItem(
            post = PostViewStatePreview.saved,
            onPostClick = {},
            onSaveClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}