package dev.weazyexe.fonto.common.data.usecase.category

import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.common.data.usecase.feed.ChangeFeedCategoryUseCase
import dev.weazyexe.fonto.common.data.usecase.feed.GetAllFeedsUseCase
import dev.weazyexe.fonto.common.model.feed.Category

class DeleteCategoryUseCase(
    private val categoryRepository: CategoryRepository,
    private val getAllFeeds: GetAllFeedsUseCase,
    private val changeFeedCategory: ChangeFeedCategoryUseCase
) {

    suspend operator fun invoke(id: Category.Id) {
        getAllFeeds().forEach {
            changeFeedCategory(feed = it, category = null)
        }

        categoryRepository.delete(id)
    }
}
