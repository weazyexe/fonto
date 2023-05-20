package dev.weazyexe.fonto.ui.features.feed.screens.categories

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.ui.features.destinations.AddEditCategoryDialogDestination
import dev.weazyexe.fonto.ui.features.destinations.CategoryDeleteConfirmationDialogDestination
import dev.weazyexe.fonto.util.handleResults
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun CategoriesScreen(
    navController: NavController,
    addEditResultRecipient: ResultRecipient<AddEditCategoryDialogDestination, Boolean>,
    deleteResultRecipient: ResultRecipient<CategoryDeleteConfirmationDialogDestination, Long?>
) {
    val viewModel = koinViewModel<CategoriesViewModel>()
    val state by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    addEditResultRecipient.handleResults { isSavedSuccessfully ->
        if (isSavedSuccessfully) {
            viewModel.loadCategories()
            viewModel.showCategorySavedDialog()
        }
    }

    deleteResultRecipient.handleResults { id ->
        if (id != null) {
            viewModel.deleteCategoryWithId(Category.Id(id))
        }
    }

    ReceiveEffect(viewModel.effects) {
        when (this) {
            is CategoriesEffect.ShowMessage -> {
                snackbarHostState.showSnackbar(context.getString(message))
            }
        }
    }

    CategoriesBody(
        categoriesLoadState = state.categoriesLoadState,
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