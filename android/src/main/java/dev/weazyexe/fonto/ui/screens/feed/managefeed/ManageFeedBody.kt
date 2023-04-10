package dev.weazyexe.fonto.ui.screens.feed.managefeed

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.R
import dev.weazyexe.fonto.ui.screens.feed.managefeed.viewstates.ManageFeedViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageFeedBody(
    manageFeedViewState: ManageFeedViewState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.manage_feed_title)) }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(
                items = manageFeedViewState.feeds,
                key = { it.id }
            ) {

            }
        }
    }
}