package dev.weazyexe.fonto.common.data.usecase.category

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

class CreateCategoryUseCase(private val categoryRepository: CategoryRepository) {

    operator fun invoke(title: String): Flow<AsyncResult<Unit>> = flowIo {
        emit(AsyncResult.Loading())
        categoryRepository.insert(title)
        emit(AsyncResult.Success(Unit))
    }
}