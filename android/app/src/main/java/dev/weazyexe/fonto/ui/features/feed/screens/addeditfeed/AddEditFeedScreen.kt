package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import dev.weazyexe.fonto.core.ui.animation.FullScreenDialogAnimationStyle
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
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

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    ReceiveEffect(viewModel.effects) {
        when (this) {
            is AddEditFeedEffect.NavigateUp -> resultBackNavigator.navigateBack(result = isSuccessful)
            is AddEditFeedEffect.ShowMessage -> {
                snackbarHostState.showSnackbar(context.getString(message))
            }
        }
    }

    AddEditFeedBody(
        title = state.title,
        link = state.link,
        isEditMode = state.id != null,
        category = state.category,
        categories = state.categories,
        snackbarHostState = snackbarHostState,
        iconLoadState = state.iconLoadState,
        finishLoadState = state.finishLoadState,
        onTitleChange = viewModel::updateTitle,
        onLinkChange = viewModel::updateLink,
        onCategoryChange = viewModel::updateCategory,
        onFinishClick = viewModel::finish,
        onBackClick = { resultBackNavigator.navigateBack(result = false) },
        onAddCategoryClick = {}
    )
}