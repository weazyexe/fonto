package dev.weazyexe.fonto.ui.features.feed.screens.feed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import dev.weazyexe.fonto.ui.features.BottomBarNavGraph
import org.koin.androidx.compose.koinViewModel

@BottomBarNavGraph(start = true)
@Destination
@Composable
fun FeedScreen(rootPaddingValues: PaddingValues) {
    val viewModel = koinViewModel<FeedViewModel>()
    val state by viewModel.uiState.collectAsState()

    FeedBody(
        newslineLoadState = state.newslineLoadState,
        rootPaddingValues = rootPaddingValues
    )
}