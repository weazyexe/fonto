package dev.weazyexe.fonto.ui.features.feed.screens.addeditcategory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.features.addeditcategory.AddEditCategoryArgs
import dev.weazyexe.fonto.features.addeditcategory.AddEditCategoryDomainState
import dev.weazyexe.fonto.features.addeditcategory.AddEditCategoryPresentation
import dev.weazyexe.fonto.ui.features.destinations.AddEditCategoryDialogDestination
import dev.weazyexe.fonto.util.flow.mapState

class AddEditCategoryViewModel(
    savedStateHandle: SavedStateHandle,
    private val presentation: AddEditCategoryPresentation
) : ViewModel() {

    private val args = AddEditCategoryDialogDestination.argsFrom(savedStateHandle)

    val state = presentation.domainState.mapState { it.asViewState() }
    val effects = presentation.effects

    init {
        presentation.onCreate(
            scope = viewModelScope,
            navigationArguments = AddEditCategoryArgs(args.id)
        )
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