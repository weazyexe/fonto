package dev.weazyexe.fonto.common.data.usecase.category

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.utils.flowIo
import kotlinx.coroutines.flow.Flow

class GetAllCategoriesUseCase(
    private val categoryRepository: CategoryRepository
) {

    operator fun invoke(): Flow<AsyncResult<List<Category>>> = flowIo {
        emit(AsyncResult.Loading())
        emit(AsyncResult.Success(categoryRepository.getAll()))
    }
}