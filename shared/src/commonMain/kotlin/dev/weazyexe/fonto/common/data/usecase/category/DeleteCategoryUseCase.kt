package dev.weazyexe.fonto.common.data.usecase.category

import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.common.model.feed.Category

class DeleteCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {

    operator fun invoke(id: Category.Id) {
        categoryRepository.delete(id)
    }
}
