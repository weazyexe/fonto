package dev.weazyexe.fonto.android.feature.feed.components.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.weazyexe.fonto.android.feature.feed.preview.PostViewStatePreview
import dev.weazyexe.fonto.common.core.asBitmap
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.formatHumanFriendly

@Composable
fun PostCompactItem(
    post: PostViewState,
    onPostClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onPostClick)
            .padding(start = 8.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BookmarkButton(
            isSaved = post.isSaved,
            onClick = onSaveClick,
            modifier = Modifier.padding(end = 8.dp)
        )

        Column(modifier = Modifier.weight(2f)) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.size(8.dp))

            PostFooter(
                icon = post.feed.icon?.asBitmap()?.asImageBitmap(),
                feedTitle = post.feed.title,
                publishedAt = post.publishedAt.formatHumanFriendly(),
                isRead = post.isRead
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        val imageModifier = Modifier
            .weight(1f)
            .aspectRatio(4f / 3f)
            .clip(RoundedCornerShape(4.dp))

        if (post.imageUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(post.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = imageModifier.background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = DrawableResources.ic_newspaper_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
        }
    }
}

@Composable
private fun PostFooter(
    icon: ImageBitmap?,
    feedTitle: String,
    publishedAt: String,
    isRead: Boolean
) {
    Row {
        if (icon != null) {
            Image(
                bitmap = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .padding(end = 4.dp)
            )
        }

        Text(
            text = buildBottomLabel(feedTitle, publishedAt),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline,
            maxLines = 1
        )

        if (isRead) {
            Icon(
                painter = painterResource(id = DrawableResources.ic_done_all_24),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .padding(start = 4.dp)
            )
        }
    }
}

@Stable
private fun buildBottomLabel(feedTitle: String, publishedAt: String?): AnnotatedString =
    buildAnnotatedString {
        append(feedTitle)
        if (publishedAt != null) {
            append(" • $publishedAt")
        }
    }

@Preview
@Composable
private fun PostCompactItemPreview() = ThemedPreview {
    PostCompactItem(
        post = PostViewStatePreview.default,
        onPostClick = {},
        onSaveClick = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun PostCompactItemNoPicturesPreview() = ThemedPreview {
    PostCompactItem(
        post = PostViewStatePreview.noPictures,
        onPostClick = {},
        onSaveClick = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun PostCompactItemSavedPreview() = ThemedPreview {
    PostCompactItem(
        post = PostViewStatePreview.saved,
        onPostClick = {},
        onSaveClick = {},
        modifier = Modifier.fillMaxWidth()
    )
}
