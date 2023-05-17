package dev.weazyexe.fonto.ui.features.feed.screens.addeditcategory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import dev.weazyexe.fonto.core.ui.animation.FullScreenDialogAnimationStyle
import org.koin.androidx.compose.koinViewModel

@Destination(style = FullScreenDialogAnimationStyle::class)
@Composable
fun AddEditCategoryScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<AddEditCategoryViewModel>()
    val state by viewModel.uiState.collectAsState()

    AddEditCategoryBody(
        isEditMode = state.id != null,
        onBackClick = navController::navigateUp,
        onSaveClick = {}
    )
}