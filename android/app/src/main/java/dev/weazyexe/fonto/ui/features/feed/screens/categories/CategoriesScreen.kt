package dev.weazyexe.fonto.ui.features.feed.screens.categories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.ui.features.destinations.AddEditCategoryDialogDestination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun CategoriesScreen(
    navController: NavController,
    resultRecipient: ResultRecipient<AddEditCategoryDialogDestination, Boolean>
) {
    val viewModel = koinViewModel<CategoriesViewModel>()
    val state by viewModel.uiState.collectAsState()

    resultRecipient.onNavResult { isSavedSuccessfully ->
        when (isSavedSuccessfully) {
            is NavResult.Canceled -> {
                // Do nothing
            }
            is NavResult.Value -> {
                if (isSavedSuccessfully.value) {
                    viewModel.loadCategories()
                }
            }
        }
    }

    CategoriesBody(
        categoriesLoadState = state.categoriesLoadState,
        onBackClick = navController::navigateUp,
        onCategoryClick = { navController.navigate(AddEditCategoryDialogDestination(it.id)) },
        onAddClick = { navController.navigate(AddEditCategoryDialogDestination()) },
        onDeleteClick = {}
    )
}