package dev.weazyexe.fonto.common.data.usecase.category

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.utils.flowIo
import kotlinx.coroutines.flow.Flow

class UpdateCategoryUseCase(private val categoryRepository: CategoryRepository) {

    operator fun invoke(category: Category): Flow<AsyncResult<Unit>> = flowIo {
        emit(AsyncResult.Loading())
        categoryRepository.update(category)
        emit(AsyncResult.Success(Unit))
    }
}