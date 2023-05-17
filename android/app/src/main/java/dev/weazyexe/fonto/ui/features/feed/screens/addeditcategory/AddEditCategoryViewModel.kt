package dev.weazyexe.fonto.ui.features.feed.screens.addeditcategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.usecase.category.CreateCategoryUseCase
import dev.weazyexe.fonto.common.data.usecase.category.GetCategoryUseCase
import dev.weazyexe.fonto.common.data.usecase.category.UpdateCategoryUseCase
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.ui.features.navArgs
import kotlinx.coroutines.launch

class AddEditCategoryViewModel(
    savedStateHandle: SavedStateHandle,
    private val getCategory: GetCategoryUseCase,
    private val createCategory: CreateCategoryUseCase,
    private val updateCategory: UpdateCategoryUseCase
) : CoreViewModel<AddEditCategoryState, AddEditCategoryEffect>() {

    private val args = savedStateHandle.navArgs<AddEditCategoryArgs>()
    override val initialState: AddEditCategoryState = AddEditCategoryState(id = args.id)

    init {
        loadCategory()
    }

    fun onTitleChange(title: String) {
        setState { copy(title = title, savingLoadState = LoadState.Data(Unit)) }
    }

    fun onSaveClick() = viewModelScope.launch {
        setState { copy(savingLoadState = LoadState.Loading()) }
        val id = state.id

        request {
            if (id != null) {
                updateCategory(Category(id = id, title = state.title))
            } else {
                createCategory(state.title)
            }
        }.withErrorHandling {
            setState { copy(savingLoadState = LoadState.Error(it)) }
        } ?: return@launch

        setState { copy(savingLoadState = LoadState.Data(Unit)) }
        AddEditCategoryEffect.NavigateUp.emit()
    }

    private fun loadCategory() = viewModelScope.launch {
        state.id?.let {
            val category = request { getCategory(it) }
                .withErrorHandling {
                    // Do nothing
                }?.data ?: return@launch

            setState { copy(title = category.title) }
        }
    }
}