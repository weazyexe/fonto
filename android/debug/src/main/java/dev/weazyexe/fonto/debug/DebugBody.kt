package dev.weazyexe.fonto.debug

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.components.ArrowBack
import dev.weazyexe.fonto.core.ui.components.preferences.TextPreferenceItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugBody(
    onBackClick: () -> Unit,
    onMockFeedClick: () -> Unit
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
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TextPreferenceItem(
                title = stringResource(id = R.string.debug_add_mock_feeds_title),
                description = stringResource(id = R.string.debug_add_mock_feeds_description),
                icon = R.drawable.ic_feed_24,
                onClick = onMockFeedClick
            )
        }
    }
}