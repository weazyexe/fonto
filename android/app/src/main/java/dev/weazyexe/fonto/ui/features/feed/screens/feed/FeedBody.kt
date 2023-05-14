package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilters
import dev.weazyexe.fonto.common.model.feed.Feed
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
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview
import dev.weazyexe.fonto.ui.features.feed.preview.PostViewStatePreview
import dev.weazyexe.fonto.ui.features.feed.screens.feed.components.FeedToolbar
import dev.weazyexe.fonto.ui.features.feed.screens.feed.components.NewslineList
import dev.weazyexe.fonto.ui.features.feed.viewstates.NewslineViewState
import dev.weazyexe.fonto.ui.features.feed.viewstates.PostViewState

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
    onFilterChange: (NewslineFilter) -> Unit,
    openDateRangePickerDialog: (NewslineFilter) -> Unit,
    openMultiplePickerDialog: (NewslineFilter) -> Unit,
    getTitleById: (Feed.Id) -> String
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val lazyListState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.padding(bottom = rootPaddingValues.calculateBottomPadding()),
        topBar = {
            FeedToolbar(
                filters = filters,
                scrollBehavior = scrollBehavior,
                lazyListState = lazyListState,
                onFilterChange = onFilterChange,
                onSearchClick = onSearchClick,
                onManageFeedClick = onManageFeedClick,
                openDateRangePickerDialog = openDateRangePickerDialog,
                openMultiplePickerDialog = openMultiplePickerDialog,
                getTitleById = getTitleById
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
        filters = NewslineFilters,
        scrollState = ScrollState(),
        rootPaddingValues = PaddingValues(),
        snackbarHostState = SnackbarHostState(),
        paginationState = PaginationState.IDLE,
        isSwipeRefreshing = false,
        onPostClick = {},
        onPostSaveClick = {},
        onScroll = {},
        onManageFeedClick = {},
        onSearchClick = {},
        onRefreshClick = {},
        fetchNextBatch = {},
        onFilterChange = {},
        openDateRangePickerDialog = {},
        openMultiplePickerDialog = {},
        getTitleById = { "" }
    )
}

@Preview
@Composable
private fun FeedBodyLoadingPreview() = ThemedPreview {
    FeedBody(
        newslineLoadState = LoadState.Loading(),
        filters = NewslineFilters,
        scrollState = ScrollState(),
        rootPaddingValues = PaddingValues(),
        snackbarHostState = SnackbarHostState(),
        paginationState = PaginationState.IDLE,
        isSwipeRefreshing = false,
        onPostClick = {},
        onPostSaveClick = {},
        onScroll = {},
        onManageFeedClick = {},
        onSearchClick = {},
        onRefreshClick = {},
        fetchNextBatch = {},
        onFilterChange = {},
        openDateRangePickerDialog = {},
        openMultiplePickerDialog = {},
        getTitleById = { "" }
    )
}
