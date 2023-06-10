package dev.weazyexe.fonto.ui.features.feed.screens.categories

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.onError
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.data.usecase.category.DeleteCategoryUseCase
import dev.weazyexe.fonto.common.data.usecase.category.GetAllCategoriesUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetAllFeedsUseCase
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.ui.features.feed.components.category.asViewState
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val getAllCategories: GetAllCategoriesUseCase,
    private val getAllFeeds: GetAllFeedsUseCase,
    private val deleteCategory: DeleteCategoryUseCase
) : CoreViewModel<CategoriesState, CategoriesEffect>() {

    override val initialState: CategoriesState = CategoriesState()

    init {
        loadCategories()
    }

    fun loadCategories() = viewModelScope.launch {
        getAllFeeds()
            .onError {
                // TODO: handle error
            }
            .filterIsInstance<AsyncResult.Success<List<Feed>>>()
            .flatMapLatest { getAllCategories() }
            .onError { setState { copy(categoriesLoadState = AsyncResult.Error(it.error)) } }
            .onSuccess { result ->
                val viewState = result.data.map { category ->
                    val amountOfFeeds = result.data.count { it == category }
                    category.asViewState(amountOfFeeds)
                }
                setState { copy(categoriesLoadState = AsyncResult.Success(viewState)) }
            }
            .launchIn(this)
    }

    fun deleteCategoryWithId(id: Category.Id) = viewModelScope.launch {
        request { deleteCategory(id, this) }
            .withErrorHandling {
                CategoriesEffect.ShowMessage(StringResources.categories_category_delete_failure)
                    .emit()
            }?.data ?: return@launch

        CategoriesEffect.ShowMessage(StringResources.categories_category_has_been_deleted).emit()
        loadCategories()
    }

    fun showCategorySavedDialog() {
        CategoriesEffect.ShowMessage(StringResources.categories_category_has_been_saved).emit()
    }
}