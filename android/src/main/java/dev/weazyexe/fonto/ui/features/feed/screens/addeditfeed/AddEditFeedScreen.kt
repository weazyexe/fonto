package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import dev.weazyexe.fonto.utils.ReceiveEffect
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun AddEditFeedScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<AddEditFeedViewModel>()
    val state by viewModel.uiState.collectAsState()

    ReceiveEffect(viewModel.effects) {
        when (this) {
            is AddEditFeedEffect.NavigateUp -> navController.navigateUp()
        }
    }

    AddEditFeedBody(
        title = state.title,
        link = state.link,
        iconLoadState = state.iconLoadState,
        finishLoadState = state.finishLoadState,
        onTitleChange = viewModel::updateTitle,
        onLinkChange = viewModel::updateLink,
        onFinishClick = viewModel::finish,
        onBackClick = { navController.navigateUp() }
    )
}