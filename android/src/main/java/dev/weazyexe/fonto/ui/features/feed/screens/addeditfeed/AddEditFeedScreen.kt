package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import dev.weazyexe.fonto.ui.core.navigation.animation.FullScreenDialogAnimationStyle
import dev.weazyexe.fonto.utils.ReceiveEffect
import org.koin.androidx.compose.koinViewModel

@Destination(
    style = FullScreenDialogAnimationStyle::class,
    navArgsDelegate = AddEditFeedScreenArgs::class
)
@Composable
fun AddEditFeedScreen(
    resultBackNavigator: ResultBackNavigator<Boolean>
) {
    val viewModel = koinViewModel<AddEditFeedViewModel>()
    val state by viewModel.uiState.collectAsState()

    ReceiveEffect(viewModel.effects) {
        when (this) {
            is AddEditFeedEffect.NavigateUp -> resultBackNavigator.navigateBack(result = isSuccessful)
        }
    }

    AddEditFeedBody(
        title = state.title,
        link = state.link,
        isEditMode = state.id != null,
        iconLoadState = state.iconLoadState,
        finishLoadState = state.finishLoadState,
        onTitleChange = viewModel::updateTitle,
        onLinkChange = viewModel::updateLink,
        onFinishClick = viewModel::finish,
        onBackClick = { resultBackNavigator.navigateBack(result = false) }
    )
}