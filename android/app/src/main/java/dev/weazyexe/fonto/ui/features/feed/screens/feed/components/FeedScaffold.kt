package dev.weazyexe.fonto.ui.features.feed.screens.feed.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.weazyexe.fonto.core.ui.components.SwipeToRefresh

@Composable
fun FeedScaffold(
    lazyListState: LazyListState,
    isSwipeRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: LazyListScope.() -> Unit
) {
    var query by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    var searchBarPaddingPx by remember { mutableStateOf(0) }
    val searchBarPadding by remember(searchBarPaddingPx) {
        derivedStateOf { with(density) { searchBarPaddingPx.toDp() + 16.dp } }
    }

    SwipeToRefresh(isRefreshing = isSwipeRefreshing, onRefresh = onRefresh) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .semantics { isContainer = true }
                    .zIndex(1f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                MaterialTheme.colorScheme.surface.copy(alpha = 0f)
                            )
                        )
                    )
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        searchBarPaddingPx = it.size.height
                    }
            ) {

                Row {
                    AnimatedVisibility(visible = !isActive) {
                        Spacer(modifier = Modifier.size(16.dp))
                    }

                    SearchToolbar(
                        query = query,
                        isActive = isActive,
                        onQueryChange = { query = it },
                        onSearch = { /*TODO*/ },
                        onActiveChange = { isActive = it },
                        modifier = Modifier.weight(1f)
                    )

                    AnimatedVisibility(visible = !isActive) {
                        Spacer(modifier = Modifier.size(16.dp))
                    }
                }
            }

            LazyColumn(state = lazyListState) {
                item {
                    Spacer(modifier = Modifier.height(searchBarPadding))
                }

                content()
            }
        }
    }
}