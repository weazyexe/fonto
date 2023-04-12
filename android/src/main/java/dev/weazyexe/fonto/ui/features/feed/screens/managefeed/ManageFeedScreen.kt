package dev.weazyexe.fonto.ui.features.feed.screens.managefeed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import dev.weazyexe.fonto.ui.features.destinations.AddEditFeedScreenDestination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun ManageFeedScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<ManageFeedViewModel>()
    val state by viewModel.uiState.collectAsState()

    ManageFeedBody(
        feedsLoadState = state.feedLoadState,
        onAddClick = { navController.navigate(AddEditFeedScreenDestination) },
        onBackClick = { navController.navigateUp() },
        onDeleteClick = { },
        onSelectClick = { }
    )
}