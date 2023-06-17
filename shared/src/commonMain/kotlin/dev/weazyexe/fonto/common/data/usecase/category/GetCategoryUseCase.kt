package dev.weazyexe.fonto.common.data.usecase.category

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

internal class GetCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {

    operator fun invoke(id: Category.Id): Flow<AsyncResult<Category>> = flowIo {
        emit(AsyncResult.Loading())
        emit(AsyncResult.Success(categoryRepository.getById(id)))
    }
}