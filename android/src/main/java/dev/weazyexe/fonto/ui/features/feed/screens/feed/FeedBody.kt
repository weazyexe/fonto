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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.ui.core.components.ErrorPane
import dev.weazyexe.fonto.ui.core.components.LoadingPane
import dev.weazyexe.fonto.ui.core.presentation.LoadState
import dev.weazyexe.fonto.ui.features.feed.components.PostItem
import dev.weazyexe.fonto.ui.features.feed.viewstates.NewslineViewState

@Composable
fun FeedBody(
    newslineLoadState: LoadState<NewslineViewState>,
    rootPaddingValues: PaddingValues,
) {
    val context = LocalContext.current
    when {
        newslineLoadState.isLoading -> LoadingPane()
        newslineLoadState.hasError() -> ErrorPane(newslineLoadState.error?.asLocalizedMessage(context))
        newslineLoadState.data != null -> NewslineList(
            newsline = newslineLoadState.data,
            paddingValues = rootPaddingValues
        )
    }
}

@Composable
private fun NewslineList(
    newsline: NewslineViewState,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
        contentPadding = WindowInsets.statusBars.asPaddingValues()
    ) {
        items(items = newsline.posts) {
            PostItem(
                post = it,
                onPostClick = { /*TODO*/ },
                onSaveClick = { /*TODO*/ },
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}