package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.ScrollState
import dev.weazyexe.fonto.core.ui.components.ErrorPane
import dev.weazyexe.fonto.core.ui.components.LoadingPane
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.ui.features.feed.components.PostItem
import dev.weazyexe.fonto.ui.features.feed.viewstates.NewslineViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

@Composable
fun FeedBody(
    newslineLoadState: LoadState<NewslineViewState>,
    scrollState: ScrollState,
    rootPaddingValues: PaddingValues,
    onScroll: (ScrollState) -> Unit
) {
    val context = LocalContext.current
    val newsline = newslineLoadState.data

    when {
        newslineLoadState.isLoading -> LoadingPane()
        newslineLoadState.hasError() -> ErrorPane(
            newslineLoadState.error?.asLocalizedMessage(
                context
            )
        )

        newsline != null -> NewslineList(
            newsline = newsline,
            scrollState = scrollState,
            paddingValues = rootPaddingValues,
            onScroll = onScroll
        )
    }
}

@Composable
private fun NewslineList(
    newsline: NewslineViewState,
    scrollState: ScrollState,
    paddingValues: PaddingValues,
    onScroll: (ScrollState) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemScrollOffset }
            .map {
                onScroll(
                    ScrollState(
                        item = lazyListState.firstVisibleItemIndex,
                        offset = lazyListState.firstVisibleItemScrollOffset
                    )
                )
            }
            .collect()
    }

    LaunchedEffect(Unit) {
        lazyListState.scrollToItem(scrollState.item, scrollState.offset)
    }

    LazyColumn(
        modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
        contentPadding = WindowInsets.statusBars.asPaddingValues(),
        state = lazyListState
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