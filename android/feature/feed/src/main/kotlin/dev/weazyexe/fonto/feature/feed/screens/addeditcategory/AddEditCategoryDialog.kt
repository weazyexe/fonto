package dev.weazyexe.fonto.feature.feed.screens.addeditcategory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import dev.weazyexe.fonto.core.ui.utils.ReceiveEffect
import dev.weazyexe.fonto.features.addeditcategory.AddEditCategoryEffect
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel

@Destination(
    style = DestinationStyle.Dialog::class,
    navArgsDelegate = AddEditCategoryScreenArgs::class
)
@Composable
fun AddEditCategoryDialog(
    resultBackNavigator: ResultBackNavigator<Boolean>
) {
    val viewModel = koinViewModel<AddEditCategoryViewModel>()
    val state by viewModel.state.collectAsState()

    HandleEffects(
        effects = viewModel.effects,
        resultBackNavigator = resultBackNavigator
    )

    AddEditCategoryBody(
        title = state.title,
        isEditMode = state.isEditMode,
        savingResult = state.savingResult,
        initResult = state.initResult,
        onCancelClick = { resultBackNavigator.navigateBack(result = false) },
        onTitleChange = viewModel::onTitleChange,
        onSaveClick = viewModel::onSaveClick
    )
}

@Composable
private fun HandleEffects(
    effects: Flow<AddEditCategoryEffect>,
    resultBackNavigator: ResultBackNavigator<Boolean>
) {
    ReceiveEffect(effects) {
        when (this) {
            is AddEditCategoryEffect.NavigateUp -> {
                resultBackNavigator.navigateBack(result = true)
            }
        }
    }
}
