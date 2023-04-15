package dev.weazyexe.fonto.ui.features.feed.screens.managefeed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.ui.features.destinations.AddEditFeedScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.DeleteConfirmationDialogDestination
import dev.weazyexe.fonto.ui.features.feed.viewstates.asFeed
import dev.weazyexe.fonto.utils.ReceiveEffect
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun ManageFeedScreen(
    navController: NavController,
    addEditFeedRecipient: ResultRecipient<AddEditFeedScreenDestination, Boolean>,
    feedDeleteRecipient: ResultRecipient<DeleteConfirmationDialogDestination, Long?>
) {
    val viewModel = koinViewModel<ManageFeedViewModel>()
    val state by viewModel.uiState.collectAsState()
    var message: Int? by remember { mutableStateOf(null) }

    addEditFeedRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {
                // Do nothing
            }
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.loadFeed()
                    viewModel.showSavedMessage()
                }
            }
        }
    }

    feedDeleteRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {
                // Do nothing
            }
            is NavResult.Value -> {
                result.value?.let { viewModel.deleteFeedById(it) }
            }
        }
    }

    ReceiveEffect(viewModel.effects) {
        when (this) {
            is ManageFeedEffect.ShowMessage -> {
                message = this.message
            }
        }
    }

    ManageFeedBody(
        feedsLoadState = state.feedLoadState,
        messageRes = message,
        onAddClick = { navController.navigate(AddEditFeedScreenDestination()) },
        onBackClick = { navController.navigateUp() },
        onClick = { navController.navigate(AddEditFeedScreenDestination(feed = it.asFeed())) },
        onDeleteClick = { navController.navigate(DeleteConfirmationDialogDestination(feed = it.asFeed())) }
    )
}