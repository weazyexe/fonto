package dev.weazyexe.fonto.ui.features.feed.screens.feed.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.ScrollState
import dev.weazyexe.fonto.core.ui.components.PaginationFooter
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPane
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPaneParams
import dev.weazyexe.fonto.core.ui.pagination.PaginationState
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.ui.features.feed.components.post.PostItem
import dev.weazyexe.fonto.ui.features.feed.components.post.PostViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.NewslineViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

@Composable
fun LazyListScope.NewslineList(
    newsline: NewslineViewState,
    lazyListState: LazyListState,
    scrollState: ScrollState,
    paginationState: PaginationState,
    onPostClick: (PostViewState) -> Unit,
    onPostSaveClick: (PostViewState) -> Unit,
    onScroll: (ScrollState) -> Unit,
    onManageFeed: () -> Unit,
    fetchNextBatch: () -> Unit,
    scope: LazyListScope,
    modifier: Modifier = Modifier
) {
    val shouldStartPaginate by remember {
        derivedStateOf {
            val lastVisibleItemIndex =
                lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val indexToStartPaginate = lazyListState.layoutInfo.totalItemsCount - 5
            paginationState == PaginationState.IDLE && lastVisibleItemIndex >= indexToStartPaginate
        }
    }

    LaunchedEffect(shouldStartPaginate) {
        if (shouldStartPaginate) {
            fetchNextBatch()
        }
    }

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

    if (newsline.posts.isEmpty()) {
        item(key = "error pane") {
            ErrorPane(
                params = ErrorPaneParams.empty(
                    message = StringResources.feed_empty_newsline,
                    action = ErrorPaneParams.Action(
                        title = StringResources.feed_empty_newsline_manage_feed,
                        onClick = onManageFeed
                    )
                )
            )
        }
    } else {
        items(items = newsline.posts, key = { it.id.origin }) {
            PostItem(
                post = it,
                onPostClick = { onPostClick(it) },
                onSaveClick = { onPostSaveClick(it) },
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        item(key = "footer") {
            PaginationFooter(
                state = paginationState,
                onRefresh = fetchNextBatch
            )
        }
    }
}

fun LazyListScope.buildNewsline(
    newsline: NewslineViewState,
    paginationState: PaginationState,
    onManageFeed: () -> Unit,
    onPostClick: (PostViewState) -> Unit,
    onPostSaveClick: (PostViewState) -> Unit,
    fetchNextBatch: () -> Unit
) {
    if (newsline.posts.isEmpty()) {
        item(key = "error pane") {
            ErrorPane(
                params = ErrorPaneParams.empty(
                    message = StringResources.feed_empty_newsline,
                    action = ErrorPaneParams.Action(
                        title = StringResources.feed_empty_newsline_manage_feed,
                        onClick = onManageFeed
                    )
                )
            )
        }
    } else {
        items(items = newsline.posts, key = { it.id.origin }) {
            PostItem(
                post = it,
                onPostClick = { onPostClick(it) },
                onSaveClick = { onPostSaveClick(it) },
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        item(key = "footer") {
            PaginationFooter(
                state = paginationState,
                onRefresh = fetchNextBatch
            )
        }
    }
}