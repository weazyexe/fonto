package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.ui.features.feed.components.PostItem
import dev.weazyexe.fonto.ui.features.feed.viewstates.NewslineViewState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedBody(
    newslineLoadState: LoadState<NewslineViewState>,
    scrollState: ScrollState,
    rootPaddingValues: PaddingValues,
    onScroll: (ScrollState) -> Unit,
    onManageFeed: () -> Unit,
    onRefresh: (isSwipeRefreshed: Boolean) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .padding(bottom = rootPaddingValues.calculateBottomPadding()),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.home_bottom_label_feed)) },
                scrollBehavior = scrollBehavior
            )
        }
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
                        onScroll = onScroll,
                        onManageFeed = onManageFeed
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
                        onScroll = onScroll,
                        onManageFeed = onManageFeed
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
    onScroll: (ScrollState) -> Unit,
    onManageFeed: () -> Unit
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