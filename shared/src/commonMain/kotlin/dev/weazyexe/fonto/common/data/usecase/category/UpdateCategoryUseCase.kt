package dev.weazyexe.fonto.common.data.usecase.category

import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.common.model.feed.Category

class UpdateCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {

    operator fun invoke(category: Category) {
        categoryRepository.update(category)
    }
}