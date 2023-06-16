package dev.weazyexe.fonto.common.data.usecase.category

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.usecase.feed.ChangeFeedCategoryUseCase
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

class DeleteCategoryUseCase(
    private val categoryRepository: CategoryRepository,
    private val feedRepository: FeedRepository,
    private val changeFeedCategory: ChangeFeedCategoryUseCase
) {

    operator fun invoke(id: Category.Id): Flow<AsyncResult<Unit>> = flowIo {
        emit(AsyncResult.Loading())
        val feeds = feedRepository.getAll()
        feeds.forEach {
            changeFeedCategory(feed = it, category = null)
        }
        categoryRepository.delete(id)
        emit(AsyncResult.Success(Unit))
    }
}
