package dev.weazyexe.fonto.debug

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.features.debug.DebugEffect
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun DebugScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<DebugViewModel>()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    ReceiveEffect(viewModel.effects) {
        when (this) {
            is DebugEffect.ShowFeedsAddedSuccessfullyMessage -> {
                snackbarHostState.showSnackbar(context.getString(StringResources.debug_feed_storage_updated))
            }
            is DebugEffect.ShowPostsDeletedSuccessfullyMessage -> {
                snackbarHostState.showSnackbar(context.getString(StringResources.debug_delete_posts_successful_message))
            }
        }
    }

    DebugBody(
        snackbarHostState = snackbarHostState,
        onBackClick = { navController.navigateUp() },
        onAddMockFeedsClick = viewModel::addMockFeeds,
        onAddPartialInvalidMockFeedsClick = viewModel::addPartialInvalidMockFeeds,
        onAddInvalidMockFeedsClick = viewModel::addInvalidMockFeeds,
        onDeletePostsClick = viewModel::deletePosts
    )
}
