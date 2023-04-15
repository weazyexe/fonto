package dev.weazyexe.fonto.ui.features.feed.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.weazyexe.fonto.R
import dev.weazyexe.fonto.ui.features.feed.preview.PostViewStatePreview
import dev.weazyexe.fonto.ui.features.feed.viewstates.PostViewState
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
                        .heightIn(max = 256.dp)
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Spacer(modifier = Modifier.size(4.dp))
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
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 15,
                overflow = TextOverflow.Ellipsis
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
                        painter = painterResource(
                            id = if (post.isSaved) {
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