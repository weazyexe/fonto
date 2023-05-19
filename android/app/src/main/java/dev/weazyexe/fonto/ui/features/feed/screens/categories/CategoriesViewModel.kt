package dev.weazyexe.fonto.ui.features.feed.screens.categories

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.usecase.category.GetAllCategoriesUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetAllFeedsUseCase
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.core.ui.presentation.LoadState
import dev.weazyexe.fonto.ui.features.feed.components.category.asViewState
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val getAllCategories: GetAllCategoriesUseCase,
    private val getAllFeeds: GetAllFeedsUseCase
) : CoreViewModel<CategoriesState, CategoriesEffect>() {

    override val initialState: CategoriesState = CategoriesState()

    init {
        loadCategories()
    }

    fun loadCategories() = viewModelScope.launch {
        val feeds = request { getAllFeeds() }
            .withErrorHandling {
                setState { copy(categoriesLoadState = LoadState.Error(it)) }
            }?.data ?: return@launch

        val categories = request { getAllCategories() }
            .withErrorHandling {
                setState { copy(categoriesLoadState = LoadState.Error(it)) }
            }?.data ?: return@launch

        val viewState = categories.map { category ->
            val amountOfFeeds = feeds.count { it.category == category }
            category.asViewState(amountOfFeeds)
        }
        setState { copy(categoriesLoadState = LoadState.Data(viewState)) }
    }

    fun showCategorySavedDialog() {
        CategoriesEffect.ShowMessage(R.string.categories_category_has_been_saved).emit()
    }
}