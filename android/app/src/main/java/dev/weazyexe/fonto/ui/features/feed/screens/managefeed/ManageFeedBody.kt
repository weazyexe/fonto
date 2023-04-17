package dev.weazyexe.fonto.ui.features.feed.screens.managefeed

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.weazyexe.fonto.R
import dev.weazyexe.fonto.core.ui.components.AnimatedAppearing
import dev.weazyexe.fonto.core.ui.components.ArrowBack
import dev.weazyexe.fonto.core.ui.components.ErrorPane
import dev.weazyexe.fonto.core.ui.components.LoadStateComponent
import dev.weazyexe.fonto.core.ui.components.LoadingPane
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.ui.features.feed.components.FeedItem
import dev.weazyexe.fonto.ui.features.feed.preview.FeedViewStatePreview
import dev.weazyexe.fonto.ui.features.feed.viewstates.FeedViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageFeedBody(
    feedsLoadState: LoadState<List<FeedViewState>>,
    @StringRes messageRes: Int?,
    onAddClick: () -> Unit,
    onBackClick: () -> Unit,
    onClick: (FeedViewState) -> Unit,
    onDeleteClick: (FeedViewState) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val message = messageRes?.let { stringResource(it) }

    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .imePadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.manage_feed_title)) },
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
                        painter = painterResource(id = R.drawable.ic_add_24),
                        contentDescription = null
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        LoadStateComponent(
            loadState = feedsLoadState,
            onSuccess = {
                FeedList(
                    list = it.data,
                    padding = padding,
                    onClick = onClick,
                    onDeleteClick = onDeleteClick
                )
            },
            onError = {
                ErrorPane(
                    message = it.error.asLocalizedMessage(
                        LocalContext.current
                    )
                )
            },
            onLoading = { LoadingPane() }
        )
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
                key = { _, item -> item.id }
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
        ErrorPane(message = stringResource(id = R.string.manage_feed_empty_feed))
    }
}

@Preview
@Composable
private fun ManageFeedBodyPreview() = dev.weazyexe.fonto.core.ui.theme.ThemedPreview {
    ManageFeedBody(
        feedsLoadState = LoadState.Data(
            listOf(
                FeedViewStatePreview.default,
                FeedViewStatePreview.noIcon,
            )
        ),
        messageRes = null,
        onAddClick = {},
        onBackClick = {},
        onClick = {},
        onDeleteClick = {}
    )
}