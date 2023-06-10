package dev.weazyexe.fonto.features.categories

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.onError
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class CategoriesPresentationImpl(
    private val dependencies: CategoriesDependencies
) : CategoriesPresentation() {

    override val initialState: CategoriesDomainState
        get() = dependencies.initialState

    override fun onCreate(scope: CoroutineScope) {
        super.onCreate(scope)
        loadFeedsAndCategories()
    }

    override fun loadFeedsAndCategories() {
        (loadFeeds() and loadCategories()).launchIn(scope)
    }

    override fun deleteById(id: Category.Id) {
        dependencies.deleteCategory(id)
            .onError { CategoriesEffect.ShowCategoryDeletionFailureMessage.emit() }
            .onSuccess {
                CategoriesEffect.ShowCategoryDeletionSuccessMessage.emit()
                loadFeedsAndCategories()
            }
            .launchIn(scope)
    }

    private fun loadFeeds() =
        dependencies.getAllFeeds()
            .onError { setState { copy(categories = AsyncResult.Error(it.error)) } }
            .onSuccess { setState { copy(feeds = it.data) } }
            .filterIsInstance<AsyncResult.Success<List<Feed>>>()

    private fun loadCategories() =
        dependencies.getAllCategories().onEach { setState { copy(categories = it) } }

    private infix fun <T, R> Flow<T>.and(other: Flow<R>): Flow<R> = flatMapLatest { other }
}