package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.ScrollState
import dev.weazyexe.fonto.core.ui.components.LoadStateComponent
import dev.weazyexe.fonto.core.ui.components.LoadingPane
import dev.weazyexe.fonto.core.ui.components.PaginationFooter
import dev.weazyexe.fonto.core.ui.components.SwipeToRefresh
import dev.weazyexe.fonto.core.ui.components.error.ErrorPane
import dev.weazyexe.fonto.core.ui.components.error.ErrorPaneParams
import dev.weazyexe.fonto.core.ui.components.error.asErrorPaneParams
import dev.weazyexe.fonto.core.ui.components.filters.FiltersRow
import dev.weazyexe.fonto.core.ui.pagination.PaginationState
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.ui.features.feed.components.PostItem
import dev.weazyexe.fonto.ui.features.feed.viewstates.NewslineViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.PostViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.asViewStates
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedBody(
    newslineLoadState: LoadState<NewslineViewState>,
    filters: List<NewslineFilter>?,
    scrollState: ScrollState,
    rootPaddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    paginationState: PaginationState,
    isSwipeRefreshing: Boolean,
    onPostClick: (PostViewState) -> Unit,
    onPostSaveClick: (PostViewState) -> Unit,
    onScroll: (ScrollState) -> Unit,
    onManageFeedClick: () -> Unit,
    onSearchClick: () -> Unit,
    onRefreshClick: (isSwipeRefreshed: Boolean) -> Unit,
    fetchNextBatch: () -> Unit,
    onFilterChange: (NewslineFilter) -> Unit
) {
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier
            .padding(bottom = rootPaddingValues.calculateBottomPadding()),
        topBar = {
            LargeTopAppBar(
                title = { Text(text = stringResource(id = R.string.home_bottom_label_feed)) },
                modifier = Modifier.clickable(
                    onClick = {
                        scope.launch { lazyListState.animateScrollToItem(0) }
                    },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search_24),
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = onManageFeedClick) {
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
            isRefreshing = isSwipeRefreshing,
            onRefresh = { onRefreshClick(true) },
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues(top = padding.calculateTopPadding()))
        ) {
            Column {
                filters?.let {
                    val filtersBarAlpha = 1f - scrollBehavior.state.collapsedFraction
                    AnimatedVisibility(visible = filtersBarAlpha != 0f) {
                        FiltersRow(
                            filters = filters.asViewStates(),
                            onBoolFilterChange = {
                                onFilterChange(it as NewslineFilter)
                            },
                            modifier = Modifier.alpha(filtersBarAlpha)
                        )
                    }
                }

                LoadStateComponent(
                    loadState = newslineLoadState,
                    onSuccess = {
                        NewslineList(
                            newsline = it,
                            scrollState = scrollState,
                            lazyListState = lazyListState,
                            paginationState = paginationState,
                            onPostClick = onPostClick,
                            onPostSaveClick = onPostSaveClick,
                            onScroll = onScroll,
                            onManageFeed = onManageFeedClick,
                            fetchNextBatch = fetchNextBatch,
                            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                        )
                    },
                    onError = {
                        ErrorPane(
                            it.error.asErrorPaneParams(
                                action = ErrorPaneParams.Action(
                                    title = R.string.error_pane_refresh,
                                    onClick = { onRefreshClick(false) }
                                )
                            )
                        )
                    },
                    onLoading = { LoadingPane() }
                )
            }
        }
    }
}

@Composable
private fun NewslineList(
    newsline: NewslineViewState,
    lazyListState: LazyListState,
    scrollState: ScrollState,
    paginationState: PaginationState,
    onPostClick: (PostViewState) -> Unit,
    onPostSaveClick: (PostViewState) -> Unit,
    onScroll: (ScrollState) -> Unit,
    onManageFeed: () -> Unit,
    fetchNextBatch: () -> Unit,
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

    LazyColumn(
        state = lazyListState,
        modifier = modifier
    ) {
        if (newsline.posts.isEmpty()) {
            item {
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
        } else {
            items(items = newsline.posts) {
                PostItem(
                    post = it,
                    onPostClick = { onPostClick(it) },
                    onSaveClick = { onPostSaveClick(it) },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            item {
                PaginationFooter(
                    state = paginationState,
                    onRefresh = fetchNextBatch
                )
            }
        }
    }
}
