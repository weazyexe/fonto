package dev.weazyexe.fonto.ui.features.feed.screens.addeditcategory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import org.koin.androidx.compose.koinViewModel

@Destination(
    style = DestinationStyle.Dialog::class,
    navArgsDelegate = AddEditCategoryArgs::class
)
@Composable
fun AddEditCategoryDialog(
    resultBackNavigator: ResultBackNavigator<Boolean>
) {
    val viewModel = koinViewModel<AddEditCategoryViewModel>()
    val state by viewModel.uiState.collectAsState()

    ReceiveEffect(viewModel.effects) {
        when (this) {
            is AddEditCategoryEffect.NavigateUp -> {
                resultBackNavigator.navigateBack(result = true)
            }
        }
    }

    AddEditCategoryBody(
        title = state.title,
        isEditMode = state.id != null,
        savingLoadState = state.savingLoadState,
        onCancelClick = { resultBackNavigator.navigateBack(result = false) },
        onTitleChange = viewModel::onTitleChange,
        onSaveClick = viewModel::onSaveClick
    )
}