package dev.weazyexe.fonto.ui.features.feed.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.map
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.features.categories.CategoriesDomainState
import dev.weazyexe.fonto.features.categories.CategoriesPresentation
import dev.weazyexe.fonto.ui.features.feed.components.category.asViewState
import kotlinx.coroutines.flow.map

class CategoriesViewModel(private val presentation: CategoriesPresentation) : ViewModel() {

    val state = presentation.domainState.map { it.asViewState() }
    val effects = presentation.effects

    init {
        presentation.onCreate(viewModelScope)
    }

    fun loadFeedAndCategories() {
        presentation.loadFeedsAndCategories()
    }

    fun deleteById(id: Category.Id) {
        presentation.deleteById(id)
    }

    private fun CategoriesDomainState.asViewState() = CategoriesViewState(
        categories = categories.map { categories ->
            categories.map { category ->
                val amountOfFeeds = feeds.count { it.category == category }
                category.asViewState(amountOfFeeds)
            }
        }
    )

    /*

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
    }*/
}