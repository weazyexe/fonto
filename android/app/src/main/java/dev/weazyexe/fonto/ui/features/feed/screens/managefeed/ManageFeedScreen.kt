package dev.weazyexe.fonto.ui.features.feed.screens.managefeed

import androidx.activity.compose.BackHandler
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.features.managefeed.ManageFeedEffect
import dev.weazyexe.fonto.ui.features.destinations.AddEditFeedScreenDestination
import dev.weazyexe.fonto.ui.features.destinations.FeedDeleteConfirmationDialogDestination
import dev.weazyexe.fonto.util.handleResults
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun ManageFeedScreen(
    navController: NavController,
    addEditFeedRecipient: ResultRecipient<AddEditFeedScreenDestination, Boolean>,
    feedDeleteRecipient: ResultRecipient<FeedDeleteConfirmationDialogDestination, Long?>,
    resultBackNavigator: ResultBackNavigator<Boolean>
) {
    val scope = rememberCoroutineScope()
    val viewModel = koinViewModel<ManageFeedViewModel>()
    val state by viewModel.state.collectAsState(ManageFeedViewState())

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    addEditFeedRecipient.handleResults { isFeedUpdated ->
        if (isFeedUpdated) {
            viewModel.loadFeed()
            viewModel.updateChangesStatus()

            scope.launch {
                snackbarHostState.showSnackbar(
                    context.getString(StringResources.manage_feed_changes_saved)
                )
            }
        }
    }

    feedDeleteRecipient.handleResults { id ->
        id?.let { viewModel.deleteFeedById(Feed.Id(it)) }
    }

    BackHandler {
        resultBackNavigator.navigateBack(result = state.hasChanges)
    }

    ReceiveEffect(viewModel.effects) {
        when (this) {
            is ManageFeedEffect.ShowDeletedSuccessfullyMessage -> {
                snackbarHostState.showSnackbar(
                    context.getString(StringResources.manage_feed_feed_deleted_successfully)
                )
            }

            is ManageFeedEffect.ShowDeletionFailedMessage -> {
                snackbarHostState.showSnackbar(
                    context.getString(StringResources.manage_feed_feed_deletion_failure)
                )
            }
        }
    }

    ManageFeedBody(
        feeds = state.feeds,
        snackbarHostState = snackbarHostState,
        onAddClick = {
            navController.navigate(AddEditFeedScreenDestination())
        },
        onBackClick = { resultBackNavigator.navigateBack(result = state.hasChanges) },
        onClick = {
            navController.navigate(
                AddEditFeedScreenDestination(feedId = it.id)
            )
        },
        onDeleteClick = {
            navController.navigate(
                FeedDeleteConfirmationDialogDestination(
                    feedId = it.id,
                    feedTitle = it.title
                )
            )
        }
    )
}
