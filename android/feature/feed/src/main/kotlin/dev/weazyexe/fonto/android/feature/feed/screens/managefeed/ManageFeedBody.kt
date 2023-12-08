package dev.weazyexe.fonto.android.feature.feed.screens.managefeed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.android.feature.feed.components.feed.FeedItem
import dev.weazyexe.fonto.android.feature.feed.components.feed.FeedViewState
import dev.weazyexe.fonto.android.feature.feed.preview.FeedViewStatePreview
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.ResponseError
import dev.weazyexe.fonto.core.ui.components.AnimatedAppearing
import dev.weazyexe.fonto.core.ui.components.ArrowBack
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPane
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPaneParams
import dev.weazyexe.fonto.core.ui.components.loadstate.LoadingPane
import dev.weazyexe.fonto.core.ui.components.loadstate.asErrorPaneParams
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.StringResources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageFeedBody(
    feeds: AsyncResult<List<FeedViewState>>,
    snackbarHostState: SnackbarHostState,
    onAddClick: () -> Unit,
    onBackClick: () -> Unit,
    onClick: (FeedViewState) -> Unit,
    onDeleteClick: (FeedViewState) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .imePadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = StringResources.manage_feed_title)) },
                navigationIcon = { ArrowBack(onBackClick) },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            AnimatedAppearing {
                FloatingActionButton(
                    onClick = onAddClick
                ) {
                    Icon(
                        painter = painterResource(id = DrawableResources.ic_add_24),
                        contentDescription = null
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        when (feeds) {
            is AsyncResult.Error -> {
                ErrorPane(
                    params = feeds.error.asErrorPaneParams(),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }

            is AsyncResult.Loading -> {
                LoadingPane(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }

            is AsyncResult.Success -> {
                FeedList(
                    list = feeds.data,
                    onClick = onClick,
                    onDeleteClick = onDeleteClick,
                    padding = padding
                )
            }
        }
    }
}

@Composable
private fun FeedList(
    list: List<FeedViewState>,
    padding: PaddingValues,
    onClick: (FeedViewState) -> Unit,
    onDeleteClick: (FeedViewState) -> Unit
) {
    val layoutDirection = LocalLayoutDirection.current
    val calculatedPaddings = PaddingValues(
        start = padding.calculateStartPadding(layoutDirection),
        top = padding.calculateTopPadding(),
        end = padding.calculateEndPadding(layoutDirection),
        bottom = padding.calculateBottomPadding() + 56.dp
    )

    if (list.isNotEmpty()) {
        LazyColumn(contentPadding = calculatedPaddings) {
            itemsIndexed(
                items = list,
                key = { _, item -> item.id.origin }
            ) { index, item ->
                FeedItem(
                    feed = item,
                    onClick = { onClick(item) },
                    onDeleteClick = { onDeleteClick(item) },
                    modifier = Modifier.fillMaxWidth()
                )

                if (index != list.size - 1) {
                    Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                }
            }
        }
    } else {
        ErrorPane(
            params = ErrorPaneParams.empty(),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun ManageFeedBodyPreview() = ThemedPreview {
    ManageFeedBody(
        feeds = AsyncResult.Success(
            listOf(
                FeedViewStatePreview.default,
                FeedViewStatePreview.noIcon,
            )
        ),
        snackbarHostState = SnackbarHostState(),
        onAddClick = {},
        onBackClick = {},
        onClick = {},
        onDeleteClick = {}
    )
}

@Preview
@Composable
private fun ManageFeedBodyEmptyPreview() = ThemedPreview {
    ManageFeedBody(
        feeds = AsyncResult.Success(emptyList()),
        snackbarHostState = SnackbarHostState(),
        onAddClick = {},
        onBackClick = {},
        onClick = {},
        onDeleteClick = {}
    )
}

@Preview
@Composable
private fun ManageFeedBodyErrorPreview() = ThemedPreview {
    ManageFeedBody(
        feeds = AsyncResult.Error(ResponseError.NoInternetError),
        snackbarHostState = SnackbarHostState(),
        onAddClick = {},
        onBackClick = {},
        onClick = {},
        onDeleteClick = {}
    )
}
