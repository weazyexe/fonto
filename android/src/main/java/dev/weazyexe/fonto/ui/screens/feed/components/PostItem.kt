package dev.weazyexe.fonto.ui.screens.feed.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.weazyexe.fonto.R
import dev.weazyexe.fonto.ui.screens.feed.viewstates.PostViewState
import dev.weazyexe.fonto.ui.theme.ThemedPreview
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostItem(
    post: PostViewState,
    onPostClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = ShapeDefaults.Medium,
        onClick = onPostClick
    ) {
        Column {
            if (post.imageUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(post.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .heightIn(max = 192.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = post.title,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = post.description,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 10
            )

            Row(
                modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (post.sourceIcon != null) {
                    Image(
                        bitmap = post.sourceIcon.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(end = 4.dp)
                    )
                }
                Text(
                    text = buildBottomLabel(post.sourceTitle, post.publishedAt),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .weight(1f),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline,
                    maxLines = 1
                )

                IconButton(
                    onClick = onSaveClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = if (post.isSaved) {
                            Icons.Default.BookmarkAdded
                        } else {
                            Icons.Default.BookmarkAdd
                        },
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Stable
private fun buildBottomLabel(sourceTitle: String, publishedAt: String?): AnnotatedString =
    buildAnnotatedString {
        append(sourceTitle)
        if (publishedAt != null) {
            append(" • $publishedAt")
        }
    }

@Preview
@Composable
private fun PostItemPreview() {
    ThemedPreview {
        PostItem(
            post = PostViewStates.default,
            onPostClick = {},
            onSaveClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

object PostViewStates {
    val default: PostViewState
        @Composable get() = PostViewState(
            id = UUID.randomUUID().toString(),
            title = "Steam перестанет работать на ПК с Windows 7, 8 и 8.1",
            description = "Поддержка прекратится 1 января 2024 года. В Valve рекомендуют обновиться на более свежую версию OC.",
            imageUrl = "https://rozetked.me/images/uploads/webp/Oe98tb9q9Ek5.webp?1679993179",
            publishedAt = "12:57, 28 Mar 2023",
            sourceTitle = "Rozetked",
            sourceIcon = ImageBitmap.imageResource(id = R.drawable.preview_favicon).asAndroidBitmap(),
            isSaved = false,
            content = null
        )
}