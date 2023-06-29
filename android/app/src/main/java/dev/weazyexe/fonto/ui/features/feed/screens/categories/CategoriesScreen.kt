package dev.weazyexe.fonto.ui.features.feed.screens.categories

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
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.features.categories.CategoriesEffect
import dev.weazyexe.fonto.ui.features.destinations.AddEditCategoryDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.CategoryDeleteConfirmationDialogDestination
import dev.weazyexe.fonto.util.handleResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun CategoriesScreen(
    navController: NavController,
    resultBackNavigator: ResultBackNavigator<Boolean>,
    addEditResultRecipient: ResultRecipient<AddEditCategoryDialogDestination, Boolean>,
    deleteResultRecipient: ResultRecipient<CategoryDeleteConfirmationDialogDestination, Long?>
) {
    val viewModel = koinViewModel<CategoriesViewModel>()
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    BackHandler {
        resultBackNavigator.navigateBack(result = state.hasChanges)
    }

    HandleNavigationResults(
        addEditResultRecipient = addEditResultRecipient,
        deleteResultRecipient = deleteResultRecipient,
        snackbarHostState = snackbarHostState,
        viewModel = viewModel
    )

    HandleEffects(
        effects = viewModel.effects,
        snackbarHostState = snackbarHostState
    )

    CategoriesBody(
        categories = state.categories,
        snackbarHostState = snackbarHostState,
        onBackClick = navController::navigateUp,
        onCategoryClick = { navController.navigate(AddEditCategoryDialogDestination(it.id)) },
        onAddClick = { navController.navigate(AddEditCategoryDialogDestination()) },
        onDeleteClick = {
            navController.navigate(
                CategoryDeleteConfirmationDialogDestination(
                    categoryId = it.id,
                    categoryTitle = it.title
                )
            )
        }
    )
}

@Composable
private fun HandleEffects(
    effects: Flow<CategoriesEffect>,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    ReceiveEffect(effects) {
        when (this) {
            CategoriesEffect.ShowCategoryDeletionFailureMessage -> {
                snackbarHostState.showSnackbar(
                    context.getString(StringResources.categories_category_delete_failure)
                )
            }

            CategoriesEffect.ShowCategoryDeletionSuccessMessage -> {
                snackbarHostState.showSnackbar(
                    context.getString(StringResources.categories_category_has_been_deleted)
                )
            }
        }
    }
}

@Composable
private fun HandleNavigationResults(
    addEditResultRecipient: ResultRecipient<AddEditCategoryDialogDestination, Boolean>,
    deleteResultRecipient: ResultRecipient<CategoryDeleteConfirmationDialogDestination, Long?>,
    snackbarHostState: SnackbarHostState,
    viewModel: CategoriesViewModel,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    addEditResultRecipient.handleResults { isSavedSuccessfully ->
        if (isSavedSuccessfully) {
            viewModel.loadFeedAndCategories()
            viewModel.updateHasChanges(true)
            scope.launch {
                snackbarHostState.showSnackbar(
                    context.getString(StringResources.categories_category_has_been_saved)
                )
            }
        }
    }

    deleteResultRecipient.handleResults { id ->
        if (id != null) {
            viewModel.deleteById(Category.Id(id))
        }
    }
}