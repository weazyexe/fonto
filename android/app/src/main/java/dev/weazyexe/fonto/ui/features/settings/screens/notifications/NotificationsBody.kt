package dev.weazyexe.fonto.ui.features.settings.screens.notifications

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.core.ui.components.ArrowBack
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPane
import dev.weazyexe.fonto.core.ui.components.loadstate.ErrorPaneParams
import dev.weazyexe.fonto.core.ui.components.loadstate.LoadingPane
import dev.weazyexe.fonto.core.ui.components.loadstate.asErrorPaneParams
import dev.weazyexe.fonto.core.ui.components.preferences.SwitchPreferenceItem
import dev.weazyexe.fonto.core.ui.theme.ThemedPreview
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.ui.features.feed.components.feed.FeedViewState
import dev.weazyexe.fonto.ui.features.feed.preview.FeedViewStatePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsBody(
    feeds: AsyncResult<List<FeedViewState>>,
    onFeedClick: (Feed.Id) -> Unit,
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .imePadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = StringResources.notifications_title)) },
                navigationIcon = { ArrowBack(onBackClick) },
                scrollBehavior = scrollBehavior
            )
        }
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
                    padding = padding,
                    onClick = onFeedClick
                )
            }
        }
    }
}

@Composable
private fun FeedList(
    list: List<FeedViewState>,
    padding: PaddingValues,
    onClick: (Feed.Id) -> Unit
) {
    if (list.isNotEmpty()) {
        LazyColumn(contentPadding = padding) {
            itemsIndexed(
                items = list,
                key = { _, item -> item.id.origin }
            ) { index, item ->
                SwitchPreferenceItem(
                    title = item.title,
                    description = null,
                    value = item.areNotificationsEnabled,
                    icon = null,
                    onValueChange = { onClick(item.id) }
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
private fun NotificationsBodyPreview() {
    ThemedPreview {
        NotificationsBody(
            feeds = AsyncResult.Success(
                listOf(
                    FeedViewStatePreview.default,
                    FeedViewStatePreview.noIcon,
                )
            ),
            onFeedClick = {},
            onBackClick = {}
        )
    }
}