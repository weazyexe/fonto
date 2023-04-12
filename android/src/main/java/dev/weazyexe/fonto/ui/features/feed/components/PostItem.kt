package dev.weazyexe.fonto.ui.features.feed.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.weazyexe.fonto.R
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
                    contentScale = ContentScale.Fit
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
private fun PostItemDefaultPreview() {
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

@Preview
@Composable
private fun PostItemNoPicturesPreview() {
    ThemedPreview {
        PostItem(
            post = PostViewStates.noPictures,
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
            post = PostViewStates.saved,
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

    val noPictures: PostViewState
        @Composable get() = PostViewState(
            id = UUID.randomUUID().toString(),
            title = "Как живет пенсионерка и библиотекарь в Подмосковье с доходом 145 000 ₽",
            description = "«У меня нет особого стимула готовить: дети многое не едят. Сын вообще, как правило, не голоден. Питается чаем, попкорном, пиццей, семечками и прочим. Но ему 26 — воспитывать уже поздно. На завтрак он вообще ест шарики „Несквик“! Карбонара — это подойдет! Жаль, что от таких ужинов я набираю вес. Другие члены семьи стройны необыкновенно»\n" +
                    "\n" +
                    "Героиня нового дневника трат живет с сыном и дочерью, сдает две квартиры и копит на летний отпуск в Турции. Вот как проходит ее неделя:",
            imageUrl = null,
            publishedAt = "12:57, 28 Mar 2023",
            sourceTitle = "Tinkoff Journal",
            sourceIcon = null,
            isSaved = false,
            content = null
        )

    val saved: PostViewState
        @Composable get() = PostViewState(
            id = UUID.randomUUID().toString(),
            title = "Российские права действуют!",
            description = "Когда они водительские, конечно же. Не зря же вы в поте лица наворачивали круги с уставшим от жизни инструктором, чтобы потом не пользоваться водительской корочкой где-нибудь на Кипре или в Хорватии.\n" +
                    "\n" +
                    "Короче, собрали список стран, где вы сможете, имея российские права, управлять бибикой:",
            imageUrl = "https://cdn4.telegram-cdn.org/file/flZogY_p55kA2Xd7RV_UykABn6DzblOJql15NmjTF688nWstLIbVi0EcUmeHZOc_8jHwdXDwNuqgUvXOPCAB5BXa0l79XqhFn_ho5jg1DMcULXNq6IIPIJAFTE_VflgY1A1H8Z9MrKlwEdDRRLz1NDH8kxm_lSD8qD9EOk3EZLr-TFKtzjt7piTNDd9Mf-L9v3e6UNNMi6nlEw4EX7WS1BFFJuB761mTf8G1r-BkzZHdlSVF2XiY8KDQjqH06TPpMICvZZpeKc2q_AlueRqowI86uWrifFdgf-yQOYLp13Q7xq3bhi_fs41nmBh5H_YxXNFZJTQ6FQusLYLzGQZGew.jpg",
            publishedAt = null,
            sourceTitle = "Aviasales",
            sourceIcon = null,
            isSaved = true,
            content = null
        )
}