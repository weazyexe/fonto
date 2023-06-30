package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.PaginationState
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPane
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPaneParams
import dev.weazyexe.fonto.core.ui.components.loadstate.LoadingPane
import dev.weazyexe.fonto.core.ui.components.loadstate.LoadingProgressPane
import dev.weazyexe.fonto.core.ui.components.loadstate.asErrorPaneParams
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.ui.features.feed.preview.PostViewStatePreview
import dev.weazyexe.fonto.ui.features.feed.screens.feed.components.FeedScaffold
import dev.weazyexe.fonto.ui.features.feed.screens.feed.components.buildPosts
import dev.weazyexe.fonto.ui.features.feed.viewstates.PostsViewState
import io.github.aakira.napier.Napier

@Composable
fun FeedBody(
    posts: AsyncResult<PostsViewState>,
    lazyListState: LazyListState,
    rootPaddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    paginationState: PaginationState,
    isSwipeRefreshing: Boolean,
    isSearchBarActive: Boolean,
    initialFirstVisibleItemIndex: Int = 0,
    initialFirstVisibleItemOffset: Int = 0,
    onPostClick: (Post.Id) -> Unit,
    onPostSaveClick: (Post.Id) -> Unit,
    onPostLoadImage: (Post.Id) -> Unit,
    onManageFeedClick: () -> Unit,
    onRefreshClick: (isSwipeRefreshed: Boolean) -> Unit,
    loadMorePosts: () -> Unit,
    onSearchBarActiveChange: (Boolean) -> Unit,
    onScroll: (firstVisibleItemIndex: Int, firstVisibleItemOffset: Int) -> Unit
) {
    var isScrollStateRestored by remember { mutableStateOf(false) }

    val shouldStartPaginate by remember(posts, paginationState) {
        derivedStateOf {
            val lastVisibleItemIndex =
                lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val indexToStartPaginate = lazyListState.layoutInfo.totalItemsCount - 5
            paginationState == PaginationState.IDLE
                    && posts is AsyncResult.Success
                    && lastVisibleItemIndex > 1
                    && lastVisibleItemIndex >= indexToStartPaginate
        }
    }

    LaunchedEffect(shouldStartPaginate) {
        if (shouldStartPaginate) {
            loadMorePosts()
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex to lazyListState.firstVisibleItemScrollOffset }
            .collect { onScroll(it.first, it.second) }
    }

    LaunchedEffect(initialFirstVisibleItemIndex, initialFirstVisibleItemOffset) {
        val currentItem = lazyListState.firstVisibleItemIndex
        val currentOffset = lazyListState.firstVisibleItemScrollOffset

        if (!isScrollStateRestored && currentItem == 0 && currentOffset == 0) {
            lazyListState.scrollToItem(initialFirstVisibleItemIndex, initialFirstVisibleItemOffset)
            isScrollStateRestored = true
        }
    }

    FeedScaffold(
        lazyListState = lazyListState,
        snackbarHostState = snackbarHostState,
        isSwipeRefreshing = isSwipeRefreshing,
        isSearchBarActive = isSearchBarActive,
        onRefresh = { onRefreshClick(true) },
        onSearchBarActiveChange = onSearchBarActiveChange,
        contentPadding = rootPaddingValues,
    ) {
        Napier.d { "KEKEK $posts" }
        when (posts) {
            is AsyncResult.Loading.Progress -> {
                item("progress_loading") {
                    LoadingProgressPane(
                        current = posts.current,
                        total = posts.total,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            is AsyncResult.Loading -> {
                item("loading") {
                    LoadingPane(modifier = Modifier.fillMaxSize())
                }
            }

            is AsyncResult.Error -> {
                item("error") {
                    ErrorPane(
                        params = posts.error.asErrorPaneParams(
                            action = ErrorPaneParams.Action(
                                title = StringResources.error_pane_refresh,
                                onClick = { onRefreshClick(false) }
                            )
                        ),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            is AsyncResult.Success -> {
                buildPosts(
                    posts = posts.data,
                    paginationState = paginationState,
                    paddingBottom = rootPaddingValues.calculateBottomPadding(),
                    onPostClick = onPostClick,
                    onPostSaveClick = onPostSaveClick,
                    onPostLoadImage = onPostLoadImage,
                    onManageFeed = onManageFeedClick,
                    loadMore = loadMorePosts,
                )
            }
        }
    }
}

@Preview
@Composable
private fun FeedBodyPreview() = ThemedPreview {
    FeedBody(
        posts = AsyncResult.Success(
            data = PostsViewState(
                posts = listOf(
                    PostViewStatePreview.default,
                    PostViewStatePreview.saved,
                    PostViewStatePreview.noPictures
                )
            )
        ),
        lazyListState = rememberLazyListState(),
        rootPaddingValues = PaddingValues(),
        snackbarHostState = SnackbarHostState(),
        paginationState = PaginationState.IDLE,
        isSwipeRefreshing = false,
        isSearchBarActive = false,
        onPostClick = {},
        onPostSaveClick = {},
        onPostLoadImage = {},
        onManageFeedClick = {},
        onRefreshClick = {},
        loadMorePosts = {},
        onSearchBarActiveChange = {},
        onScroll = { _, _ -> }
    )
}

@Preview
@Composable
private fun FeedBodyLoadingPreview() = ThemedPreview {
    FeedBody(
        posts = AsyncResult.Loading(),
        lazyListState = rememberLazyListState(),
        rootPaddingValues = PaddingValues(),
        snackbarHostState = SnackbarHostState(),
        paginationState = PaginationState.IDLE,
        isSwipeRefreshing = false,
        isSearchBarActive = false,
        onPostClick = {},
        onPostSaveClick = {},
        onPostLoadImage = {},
        onManageFeedClick = {},
        onRefreshClick = {},
        loadMorePosts = {},
        onSearchBarActiveChange = {},
        onScroll = { _, _ -> }
    )
}
