package dev.weazyexe.fonto.ui.features.feed.screens.managefeed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.R
import dev.weazyexe.fonto.ui.core.components.ArrowBack
import dev.weazyexe.fonto.ui.core.components.ErrorPane
import dev.weazyexe.fonto.ui.core.components.LoadingPane
import dev.weazyexe.fonto.ui.core.presentation.LoadState
import dev.weazyexe.fonto.ui.features.feed.components.FeedItem
import dev.weazyexe.fonto.ui.features.feed.viewstates.FeedViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageFeedBody(
    feedsLoadState: LoadState<List<FeedViewState>>,
    onAddClick: () -> Unit,
    onBackClick: () -> Unit,
    onClick: (FeedViewState) -> Unit,
    onDeleteClick: (FeedViewState) -> Unit,
    onSelectClick: (FeedViewState) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.manage_feed_title)) },
                navigationIcon = { ArrowBack(onBackClick) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
    ) { padding ->
        when {
            feedsLoadState.isLoading -> LoadingPane()
            feedsLoadState.error != null -> ErrorPane(
                message = feedsLoadState.error.asLocalizedMessage(
                    LocalContext.current
                )
            )
            feedsLoadState.data != null -> FeedList(
                list = feedsLoadState.data,
                padding = padding,
                onClick = onClick,
                onDeleteClick = onDeleteClick,
                onSelectClick = onSelectClick,
            )
        }
    }
}

@Composable
private fun FeedList(
    list: List<FeedViewState>,
    padding: PaddingValues,
    onClick: (FeedViewState) -> Unit,
    onDeleteClick: (FeedViewState) -> Unit,
    onSelectClick: (FeedViewState) -> Unit,
) {
    if (list.isNotEmpty()) {
        LazyColumn(contentPadding = padding) {
            items(
                items = list,
                key = { it.id }
            ) {
                FeedItem(
                    feed = it,
                    onClick = { onClick(it) },
                    onDeleteClick = { onDeleteClick(it) },
                    onSelectClick = { onSelectClick(it) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    } else {
        ErrorPane(message = stringResource(id = R.string.manage_feed_empty_feed))
    }
}