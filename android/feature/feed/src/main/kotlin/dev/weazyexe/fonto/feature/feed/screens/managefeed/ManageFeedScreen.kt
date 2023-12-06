package dev.weazyexe.fonto.feature.feed.screens.managefeed

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
import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.core.ui.utils.handleResults
import dev.weazyexe.fonto.feature.feed.screens.destinations.AddEditFeedScreenDestination
import dev.weazyexe.fonto.feature.feed.screens.destinations.FeedDeleteConfirmationDialogDestination
import dev.weazyexe.fonto.features.managefeed.ManageFeedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun ManageFeedScreen(
    navController: NavController,
    addEditFeedRecipient: ResultRecipient<AddEditFeedScreenDestination, Boolean>,
    feedDeleteRecipient: ResultRecipient<FeedDeleteConfirmationDialogDestination, Long?>
) {
    val viewModel = koinViewModel<ManageFeedViewModel>()
    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    HandleNavigationResults(
        addEditFeedRecipient = addEditFeedRecipient,
        feedDeleteRecipient = feedDeleteRecipient,
        snackbarHostState = snackbarHostState,
        viewModel = viewModel
    )

    HandleEffects(
        effects = viewModel.effects,
        snackbarHostState = snackbarHostState
    )

    ManageFeedBody(
        feeds = state.feeds,
        snackbarHostState = snackbarHostState,
        onAddClick = {
            navController.navigate(AddEditFeedScreenDestination())
        },
        onBackClick = { navController.navigateUp() },
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

@Composable
private fun HandleEffects(
    effects: Flow<ManageFeedEffect>,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    ReceiveEffect(effects) {
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
}

@Composable
private fun HandleNavigationResults(
    addEditFeedRecipient: ResultRecipient<AddEditFeedScreenDestination, Boolean>,
    feedDeleteRecipient: ResultRecipient<FeedDeleteConfirmationDialogDestination, Long?>,
    snackbarHostState: SnackbarHostState,
    viewModel: ManageFeedViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

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
}
