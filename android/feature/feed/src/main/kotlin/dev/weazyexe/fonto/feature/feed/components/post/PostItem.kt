package dev.weazyexe.fonto.feature.feed.components.post

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview
import dev.weazyexe.fonto.feature.feed.preview.PostViewStatePreview

@Composable
fun PostItem(
    post: PostViewState,
    onPostClick: () -> Unit,
    onSaveClick: () -> Unit,
    type: PostItemType,
    modifier: Modifier = Modifier,
    loadPostMetadata: (Post.Id) -> Unit = {}
) {

    LaunchedEffect(post.imageUrl) {
        if (post.imageUrl == null && post.shouldTryToLoadMetadata) {
            loadPostMetadata(post.id)
        }
    }

    when (type) {
        PostItemType.COMPACT ->
            PostCompactItem(
                post = post,
                onPostClick = onPostClick,
                onSaveClick = onSaveClick
            )

        PostItemType.REGULAR ->
            PostRegularItem(
                post = post,
                onPostClick = onPostClick,
                onSaveClick = onSaveClick,
                modifier = modifier
                    .animateContentSize()
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
                    .clickable(onClick = onPostClick)
            )
    }
}

@Preview
@Composable
private fun PostItemRegularPreview() {
    ThemedPreview {
        PostItem(
            post = PostViewStatePreview.default,
            onPostClick = {},
            onSaveClick = {},
            type = PostItemType.REGULAR,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun PostItemCompactPreview() {
    ThemedPreview {
        PostItem(
            post = PostViewStatePreview.default,
            onPostClick = {},
            onSaveClick = {},
            type = PostItemType.COMPACT,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}
