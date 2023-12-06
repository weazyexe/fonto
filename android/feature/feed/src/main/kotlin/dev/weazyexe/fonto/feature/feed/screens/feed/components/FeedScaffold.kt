package dev.weazyexe.fonto.feature.feed.screens.feed.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.weazyexe.fonto.core.ui.components.SwipeToRefresh
import dev.weazyexe.fonto.feature.feed.screens.feed.components.search.SearchOverlay

@Composable
fun FeedScaffold(
    lazyListState: LazyListState,
    snackbarHostState: SnackbarHostState,
    isSwipeRefreshing: Boolean,
    isSearchBarActive: Boolean,
    userScrollEnabled: Boolean,
    onRefresh: () -> Unit,
    onSearchBarActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    content: LazyListScope.() -> Unit
) {
    val density = LocalDensity.current
    var searchBarPaddingPx by remember { mutableStateOf(0) }
    val searchBarPadding by remember(searchBarPaddingPx) {
        derivedStateOf { with(density) { searchBarPaddingPx.toDp() + 16.dp } }
    }

    SwipeToRefresh(
        isRefreshing = isSwipeRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
        contentPadding = contentPadding,
        isEnabled = !isSearchBarActive
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
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
                SearchOverlay(
                    isActive = isSearchBarActive,
                    onSearchBarActiveChange = onSearchBarActiveChange,
                    contentPadding = contentPadding
                )
            }

            LazyColumn(
                state = lazyListState,
                modifier = Modifier.testTag("posts_list"),
                userScrollEnabled = userScrollEnabled
            ) {
                item {
                    Spacer(modifier = Modifier.height(searchBarPadding))
                }

                content()
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = contentPadding.calculateBottomPadding())
            ) {
                Snackbar(
                    snackbarData = it,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
