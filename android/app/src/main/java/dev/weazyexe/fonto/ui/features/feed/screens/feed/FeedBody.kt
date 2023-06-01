package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.weazyexe.fonto.core.ui.ScrollState
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPane
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPaneParams
import dev.weazyexe.fonto.core.ui.components.loadstate.LoadingPane
import dev.weazyexe.fonto.core.ui.components.loadstate.asErrorPaneParams
import dev.weazyexe.fonto.core.ui.pagination.PaginationState
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.ui.features.feed.components.post.PostViewState
import dev.weazyexe.fonto.ui.features.feed.preview.PostViewStatePreview
import dev.weazyexe.fonto.ui.features.feed.screens.feed.components.FeedScaffold
import dev.weazyexe.fonto.ui.features.feed.screens.feed.components.buildNewsline
import dev.weazyexe.fonto.ui.features.feed.viewstates.NewslineViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

@Composable
fun FeedBody(
    newslineLoadState: LoadState<NewslineViewState>,
    scrollState: ScrollState,
    rootPaddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    paginationState: PaginationState,
    isSwipeRefreshing: Boolean,
    isSearchBarActive: Boolean,
    onPostClick: (PostViewState) -> Unit,
    onPostSaveClick: (PostViewState) -> Unit,
    onScroll: (ScrollState) -> Unit,
    onManageFeedClick: () -> Unit,
    onRefreshClick: (isSwipeRefreshed: Boolean) -> Unit,
    fetchNextBatch: () -> Unit,
    onSearchBarActiveChange: (Boolean) -> Unit,
) {
    val lazyListState = rememberLazyListState()

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

    FeedScaffold(
        lazyListState = lazyListState,
        snackbarHostState = snackbarHostState,
        isSwipeRefreshing = isSwipeRefreshing,
        isSearchBarActive = isSearchBarActive,
        onRefresh = { onRefreshClick(true) },
        onSearchBarActiveChange = onSearchBarActiveChange,
        onPostClick = onPostClick,
        onPostSaveClick = onPostSaveClick,
        contentPadding = rootPaddingValues
    ) {
        when (newslineLoadState) {
            is LoadState.Loading -> {
                item("loading") {
                    LoadingPane(modifier = Modifier.fillMaxSize())
                }
            }

            is LoadState.Error -> {
                item("error") {
                    ErrorPane(
                        params = newslineLoadState.error.asErrorPaneParams(
                            action = ErrorPaneParams.Action(
                                title = StringResources.error_pane_refresh,
                                onClick = { onRefreshClick(false) }
                            )
                        ),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            is LoadState.Data -> {
                buildNewsline(
                    newsline = newslineLoadState.data,
                    paginationState = paginationState,
                    onPostClick = onPostClick,
                    onPostSaveClick = onPostSaveClick,
                    onManageFeed = onManageFeedClick,
                    fetchNextBatch = fetchNextBatch,
                )
            }
        }
    }
}

@Preview
@Composable
private fun FeedBodyPreview() = ThemedPreview {
    FeedBody(
        newslineLoadState = LoadState.Data(
            data = NewslineViewState(
                posts = listOf(
                    PostViewStatePreview.default,
                    PostViewStatePreview.saved,
                    PostViewStatePreview.noPictures
                )
            )
        ),
        scrollState = ScrollState(),
        rootPaddingValues = PaddingValues(),
        snackbarHostState = SnackbarHostState(),
        paginationState = PaginationState.IDLE,
        isSwipeRefreshing = false,
        isSearchBarActive = false,
        onPostClick = {},
        onPostSaveClick = {},
        onScroll = {},
        onManageFeedClick = {},
        onRefreshClick = {},
        fetchNextBatch = {},
        onSearchBarActiveChange = {}
    )
}

@Preview
@Composable
private fun FeedBodyLoadingPreview() = ThemedPreview {
    FeedBody(
        newslineLoadState = LoadState.Loading(),
        scrollState = ScrollState(),
        rootPaddingValues = PaddingValues(),
        snackbarHostState = SnackbarHostState(),
        paginationState = PaginationState.IDLE,
        isSwipeRefreshing = false,
        isSearchBarActive = false,
        onPostClick = {},
        onPostSaveClick = {},
        onScroll = {},
        onManageFeedClick = {},
        onRefreshClick = {},
        fetchNextBatch = {},
        onSearchBarActiveChange = {}
    )
}
