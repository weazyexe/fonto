package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import dev.weazyexe.fonto.ui.features.BottomBarNavGraph
import dev.weazyexe.fonto.ui.features.destinations.ManageFeedScreenDestination

@BottomBarNavGraph(start = true)
@Destination
@Composable
fun FeedScreen(
    rootPaddingValues: PaddingValues,
    viewModel: FeedViewModel,
    navigateTo: (DirectionDestinationSpec) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    FeedBody(
        newslineLoadState = state.newslineLoadState,
        scrollState = state.scrollState,
        rootPaddingValues = rootPaddingValues,
        onScroll = viewModel::onScroll,
        onManageFeed = { navigateTo(ManageFeedScreenDestination) },
        onRefreshClick = viewModel::loadNewsline
    )
}