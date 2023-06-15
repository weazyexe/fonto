package dev.weazyexe.fonto.ui.features.feed.screens.categories

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
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.core.ui.utils.ReceiveNewEffect
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.features.categories.CategoriesEffect
import dev.weazyexe.fonto.ui.features.destinations.AddEditCategoryDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.CategoryDeleteConfirmationDialogDestination
import dev.weazyexe.fonto.util.handleResults
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun CategoriesScreen(
    navController: NavController,
    addEditResultRecipient: ResultRecipient<AddEditCategoryDialogDestination, Boolean>,
    deleteResultRecipient: ResultRecipient<CategoryDeleteConfirmationDialogDestination, Long?>
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel = koinViewModel<CategoriesViewModel>()
    val state by viewModel.state.collectAsState(CategoriesViewState())
    val snackbarHostState = remember { SnackbarHostState() }

    addEditResultRecipient.handleResults { isSavedSuccessfully ->
        if (isSavedSuccessfully) {
            viewModel.loadFeedAndCategories()
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

    ReceiveNewEffect(viewModel.effects) {
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