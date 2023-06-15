package dev.weazyexe.fonto.ui.features.feed.screens.addeditcategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.features.addeditcategory.AddEditCategoryArgs
import dev.weazyexe.fonto.features.addeditcategory.AddEditCategoryDomainState
import dev.weazyexe.fonto.features.addeditcategory.AddEditCategoryPresentation
import dev.weazyexe.fonto.ui.features.destinations.AddEditCategoryDialogDestination
import kotlinx.coroutines.flow.map

class AddEditCategoryViewModel(
    savedStateHandle: SavedStateHandle,
    private val presentation: AddEditCategoryPresentation
) : ViewModel() {

    private val args = AddEditCategoryDialogDestination.argsFrom(savedStateHandle)

    val state = presentation.domainState.map { it.asViewState() }
    val effects = presentation.effects

    init {
        presentation.onCreate(viewModelScope, AddEditCategoryArgs(args.id))
    }

    fun onTitleChange(title: String) {
        presentation.onTitleChange(title)
    }

    fun onSaveClick() {
        presentation.saveChanges()
    }

    private fun AddEditCategoryDomainState.asViewState() =
        AddEditCategoryViewState(
            title = title,
            isEditMode = id != null,
            savingResult = savingResult,
            initResult = initResult
        )
}