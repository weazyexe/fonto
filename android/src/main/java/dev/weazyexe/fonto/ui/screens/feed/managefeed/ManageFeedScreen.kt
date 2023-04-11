package dev.weazyexe.fonto.ui.screens.feed.managefeed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun ManageFeedScreen() {
    val viewModel = koinViewModel<ManageFeedViewModel>()
    val state by viewModel.uiState.collectAsState()

    ManageFeedBody(
        feedsLoadState = state.feedLoadState
    )
}