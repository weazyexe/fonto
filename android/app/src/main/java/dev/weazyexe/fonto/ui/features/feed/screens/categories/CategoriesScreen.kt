package dev.weazyexe.fonto.ui.features.feed.screens.categories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import dev.weazyexe.fonto.ui.features.destinations.AddEditCategoryScreenDestination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun CategoriesScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<CategoriesViewModel>()
    val state by viewModel.uiState.collectAsState()

    CategoriesBody(
        categoriesLoadState = state.categoriesLoadState,
        onBackClick = navController::navigateUp,
        onCategoryClick = {},
        onAddClick = { navController.navigate(AddEditCategoryScreenDestination()) },
        onDeleteClick = {}
    )
}