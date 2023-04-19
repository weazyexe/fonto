package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.ScrollState
import dev.weazyexe.fonto.core.ui.components.LoadStateComponent
import dev.weazyexe.fonto.core.ui.components.LoadingPane
import dev.weazyexe.fonto.core.ui.components.SwipeToRefresh
import dev.weazyexe.fonto.core.ui.components.error.ErrorPane
import dev.weazyexe.fonto.core.ui.components.error.ErrorPaneParams
import dev.weazyexe.fonto.core.ui.components.error.asErrorPaneParams
import dev.weazyexe.fonto.core.ui.pagination.PaginationState
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.ui.features.feed.components.PostItem
import dev.weazyexe.fonto.ui.features.feed.viewstates.NewslineViewState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedBody(
    newslineLoadState: LoadState<NewslineViewState>,
    scrollState: ScrollState,
    rootPaddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    paginationState: PaginationState,
    onScroll: (ScrollState) -> Unit,
    onManageFeed: () -> Unit,
    onRefresh: (isSwipeRefreshed: Boolean) -> Unit,
    fetchNextBatch: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .padding(bottom = rootPaddingValues.calculateBottomPadding()),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.home_bottom_label_feed)) },
                actions = {
                    IconButton(onClick = onManageFeed) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_controls_24),
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        SwipeToRefresh(
            isRefreshing = newslineLoadState is LoadState.Loading.SwipeRefresh,
            onRefresh = { onRefresh(true) },
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(PaddingValues(top = padding.calculateTopPadding()))
        ) {
            LoadStateComponent(
                loadState = newslineLoadState,
                onSuccess = {
                    NewslineList(
                        newsline = it.data,
                        scrollState = scrollState,
                        paginationState = paginationState,
                        onScroll = onScroll,
                        onManageFeed = onManageFeed,
                        fetchNextBatch = fetchNextBatch
                    )
                },
                onError = {
                    ErrorPane(
                        it.error.asErrorPaneParams(
                            action = ErrorPaneParams.Action(
                                title = R.string.error_pane_refresh,
                                onClick = { onRefresh(false) }
                            )
                        )
                    )
                },
                onLoading = { LoadingPane() },
                onSwipeRefresh = {
                    NewslineList(
                        newsline = it ?: NewslineViewState(),
                        scrollState = scrollState,
                        paginationState = paginationState,
                        onScroll = onScroll,
                        onManageFeed = onManageFeed,
                        fetchNextBatch = fetchNextBatch
                    )
                }
            )
        }
    }
}

@Composable
private fun NewslineList(
    newsline: NewslineViewState,
    scrollState: ScrollState,
    paginationState: PaginationState,
    onScroll: (ScrollState) -> Unit,
    onManageFeed: () -> Unit,
    fetchNextBatch: () -> Unit
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
        Napier.d { "KEKEK Should paginate updated" }
        if (shouldStartPaginate) {
            Napier.d { "KEKEK Should paginate. ACTION!" }
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

    if (newsline.posts.isNotEmpty()) {
        LazyColumn(
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
            item {
                when (paginationState) {
                    PaginationState.LOADING ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    else -> {

                    }
                }
            }
        }
    } else {
        ErrorPane(
            params = ErrorPaneParams.empty(
                message = R.string.feed_empty_newsline,
                action = ErrorPaneParams.Action(
                    title = R.string.feed_empty_newsline_manage_feed,
                    onClick = onManageFeed
                )
            )
        )
    }
}