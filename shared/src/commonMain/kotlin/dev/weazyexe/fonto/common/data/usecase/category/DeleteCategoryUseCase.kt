package dev.weazyexe.fonto.common.data.usecase.category

import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.common.data.usecase.feed.ChangeFeedCategoryUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetAllFeedsUseCase
import dev.weazyexe.fonto.common.model.feed.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn

class DeleteCategoryUseCase(
    private val categoryRepository: CategoryRepository,
    private val getAllFeeds: GetAllFeedsUseCase,
    private val changeFeedCategory: ChangeFeedCategoryUseCase
) {

    operator fun invoke(id: Category.Id, scope: CoroutineScope) {
        getAllFeeds().onSuccess { result ->
            result.data.forEach {
                changeFeedCategory(feed = it, category = null)
            }
            categoryRepository.delete(id)
        }.launchIn(scope)
    }
}
