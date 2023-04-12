package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.ui.features.feed.components.PostItem
import dev.weazyexe.fonto.ui.features.feed.viewstates.PostsViewState

@Composable
fun FeedBody(
    postsViewState: PostsViewState,
    rootPaddingValues: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier.padding(bottom = rootPaddingValues.calculateBottomPadding()),
        contentPadding = WindowInsets.statusBars.asPaddingValues()
    ) {
        items(items = postsViewState.posts) {
            PostItem(
                post = it,
                onPostClick = { /*TODO*/ },
                onSaveClick = { /*TODO*/ },
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}