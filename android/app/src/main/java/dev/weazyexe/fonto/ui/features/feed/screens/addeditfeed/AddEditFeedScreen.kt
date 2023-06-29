package dev.weazyexe.fonto.ui.features.feed.screens.addeditfeed

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
import dev.weazyexe.fonto.core.ui.animation.FullScreenDialogAnimationStyle
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.features.addeditfeed.AddEditFeedEffect
import dev.weazyexe.fonto.ui.features.destinations.AddEditCategoryDialogDestination
import dev.weazyexe.fonto.util.handleResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Destination(
    style = FullScreenDialogAnimationStyle::class,
    navArgsDelegate = AddEditFeedScreenArgs::class
)
@Composable
fun AddEditFeedScreen(
    navController: NavController,
    resultBackNavigator: ResultBackNavigator<Boolean>,
    resultRecipient: ResultRecipient<AddEditCategoryDialogDestination, Boolean>
) {
    val viewModel = koinViewModel<AddEditFeedViewModel>()
    val state by viewModel.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    HandleNavigationResults(
        addEditResultRecipient = resultRecipient,
        snackbarHostState = snackbarHostState,
        viewModel = viewModel
    )

    HandleEffects(
        effects = viewModel.effects,
        resultBackNavigator = resultBackNavigator,
        snackbarHostState = snackbarHostState
    )

    AddEditFeedBody(
        title = state.title,
        link = state.link,
        isEditMode = state.isEditMode,
        category = state.category,
        categories = state.categories,
        snackbarHostState = snackbarHostState,
        icon = state.icon,
        finishResult = state.finishResult,
        onTitleChange = viewModel::updateTitle,
        onLinkChange = viewModel::updateLink,
        onCategoryChange = viewModel::updateCategory,
        onFinishClick = viewModel::finish,
        onBackClick = { resultBackNavigator.navigateBack(result = false) },
        onAddCategoryClick = { navController.navigate(AddEditCategoryDialogDestination()) }
    )
}

@Composable
private fun HandleEffects(
    effects: Flow<AddEditFeedEffect>,
    resultBackNavigator: ResultBackNavigator<Boolean>,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    ReceiveEffect(effects) {
        when (this) {
            is AddEditFeedEffect.NavigateUp -> {
                resultBackNavigator.navigateBack(result = isSuccessful)
            }

            is AddEditFeedEffect.ShowFeedLoadingFailureMessage -> {
                snackbarHostState.showSnackbar(context.getString(StringResources.add_edit_feed_feed_loading_failure))
            }

            is AddEditFeedEffect.ShowCategoriesLoadingFailureMessage -> {
                snackbarHostState.showSnackbar(context.getString(StringResources.add_edit_feed_categories_loading_failure))
            }

            is AddEditFeedEffect.ShowFaviconLoadingFailureMessage -> {
                snackbarHostState.showSnackbar(context.getString(StringResources.add_edit_feed_feed_icon_loading_failure))
            }

            is AddEditFeedEffect.ShowTitleInvalidMessage -> {
                snackbarHostState.showSnackbar(context.getString(StringResources.add_edit_feed_invalid_title))
            }

            is AddEditFeedEffect.ShowLinkInvalidMessage -> {
                snackbarHostState.showSnackbar(context.getString(StringResources.add_edit_feed_invalid_link))
            }

            is AddEditFeedEffect.ShowFeedValidationError -> {
                snackbarHostState.showSnackbar(context.getString(StringResources.add_edit_feed_feed_link_is_invalid))
            }

            is AddEditFeedEffect.ShowFeedSavingError -> {
                snackbarHostState.showSnackbar(context.getString(StringResources.add_edit_feed_feed_saving_error))
            }
        }
    }
}

@Composable
private fun HandleNavigationResults(
    addEditResultRecipient: ResultRecipient<AddEditCategoryDialogDestination, Boolean>,
    snackbarHostState: SnackbarHostState,
    viewModel: AddEditFeedViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    addEditResultRecipient.handleResults { isCategoryAdded ->
        if (isCategoryAdded) {
            viewModel.loadCategories()

            scope.launch {
                snackbarHostState.showSnackbar(
                    context.getString(StringResources.categories_category_has_been_saved)
                )
            }
        }
    }
}