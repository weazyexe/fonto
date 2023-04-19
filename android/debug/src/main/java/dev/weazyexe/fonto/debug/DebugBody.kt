package dev.weazyexe.fonto.debug

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.components.ArrowBack
import dev.weazyexe.fonto.core.ui.components.preferences.PreferencesGroup
import dev.weazyexe.fonto.core.ui.components.preferences.TextPreferenceItem
import dev.weazyexe.fonto.debug.mock.FULLY_INVALID_FEED
import dev.weazyexe.fonto.debug.mock.PARTIALLY_INVALID_FEED
import dev.weazyexe.fonto.debug.mock.VALID_FEED

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugBody(
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onMockFeedClick: (List<Feed>) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.debug_title)) },
                navigationIcon = { ArrowBack(onBackClick) },
                scrollBehavior = scrollBehavior
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        PreferencesGroup(
            title = stringResource(id = R.string.debug_feed_group),
            modifier = Modifier.padding(padding)
        ) {
            TextPreferenceItem(
                title = stringResource(id = R.string.debug_add_mock_feeds_title),
                description = stringResource(id = R.string.debug_add_mock_feeds_description),
                icon = R.drawable.ic_feed_24,
                onClick = { onMockFeedClick(VALID_FEED) },
                modifier = Modifier.fillMaxWidth()
            )

            TextPreferenceItem(
                title = stringResource(id = R.string.debug_add_mock_partially_valid_feeds_title),
                description = stringResource(id = R.string.debug_add_mock_partially_valid_feeds_description),
                icon = R.drawable.ic_rule_24,
                onClick = { onMockFeedClick(PARTIALLY_INVALID_FEED) },
                modifier = Modifier.fillMaxWidth()
            )

            TextPreferenceItem(
                title = stringResource(id = R.string.debug_add_mock_invalid_feeds_title),
                description = stringResource(id = R.string.debug_add_mock_invalid_feeds_description),
                icon = R.drawable.ic_warning_24,
                onClick = { onMockFeedClick(FULLY_INVALID_FEED) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}