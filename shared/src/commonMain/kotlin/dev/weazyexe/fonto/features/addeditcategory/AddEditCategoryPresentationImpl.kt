package dev.weazyexe.fonto.features.addeditcategory

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.ResponseError
import dev.weazyexe.fonto.common.data.onError
import dev.weazyexe.fonto.common.data.onLoading
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.model.feed.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class AddEditCategoryPresentationImpl(
    private val dependencies: AddEditCategoryDependencies
) : AddEditCategoryPresentation() {

    override val initialState: AddEditCategoryDomainState
        get() = dependencies.initialState


    override fun onCreate(scope: CoroutineScope, navigationArguments: AddEditCategoryArgs) {
        super.onCreate(scope, navigationArguments)
        navigationArguments.id?.let { loadCategory(it) }
    }

    override fun onTitleChange(title: String) {
        setState { copy(title = title) }
    }

    override fun saveChanges() {
        if (!dependencies.titleValidator.validate(state.title)) {
            setState { copy(savingResult = AsyncResult.Error(ResponseError.InvalidTitle)) }
            return
        }

        (state.id
            ?.let { dependencies.updateCategory(Category(id = it, title = state.title.trim())) }
            ?: dependencies.createCategory(state.title.trim()))
            .onEach { setState { copy(savingResult = it) } }
            .onSuccess { AddEditCategoryEffect.NavigateUp.emit() }
            .launchIn(scope)
    }

    private fun loadCategory(id: Category.Id) {
        dependencies.getCategory(id)
            .onLoading { setState { copy(initResult = AsyncResult.Loading()) } }
            .onError { setState { copy(initResult = AsyncResult.Error(it.error)) } }
            .onSuccess {
                setState {
                    copy(
                        id = it.data.id,
                        title = it.data.title,
                        initResult = AsyncResult.Success(Unit)
                    )
                }
            }
            .launchIn(scope)
    }
}
