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
import dev.weazyexe.fonto.core.ui.components.ArrowBack
import dev.weazyexe.fonto.core.ui.components.preferences.PreferencesGroup
import dev.weazyexe.fonto.core.ui.components.preferences.TextPreferenceItem
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.StringResources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugBody(
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onAddMockFeedsClick: () -> Unit,
    onAddPartialInvalidMockFeedsClick: () -> Unit,
    onAddInvalidMockFeedsClick: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = StringResources.debug_title)) },
                navigationIcon = { ArrowBack(onBackClick) },
                scrollBehavior = scrollBehavior
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        PreferencesGroup(
            title = stringResource(id = StringResources.debug_feed_group),
            modifier = Modifier.padding(padding)
        ) {
            TextPreferenceItem(
                title = stringResource(id = StringResources.debug_add_mock_feeds_title),
                description = stringResource(id = StringResources.debug_add_mock_feeds_description),
                icon = DrawableResources.ic_feed_24,
                onClick = onAddMockFeedsClick,
                modifier = Modifier.fillMaxWidth()
            )

            TextPreferenceItem(
                title = stringResource(id = StringResources.debug_add_mock_partially_valid_feeds_title),
                description = stringResource(id = StringResources.debug_add_mock_partially_valid_feeds_description),
                icon = DrawableResources.ic_rule_24,
                onClick = onAddPartialInvalidMockFeedsClick,
                modifier = Modifier.fillMaxWidth()
            )

            TextPreferenceItem(
                title = stringResource(id = StringResources.debug_add_mock_invalid_feeds_title),
                description = stringResource(id = StringResources.debug_add_mock_invalid_feeds_description),
                icon = DrawableResources.ic_warning_24,
                onClick = onAddInvalidMockFeedsClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}