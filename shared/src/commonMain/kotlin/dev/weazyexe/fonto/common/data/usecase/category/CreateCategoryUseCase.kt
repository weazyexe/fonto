package dev.weazyexe.fonto.common.data.usecase.category

import dev.weazyexe.fonto.common.data.repository.CategoryRepository

class CreateCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {

    operator fun invoke(title: String) {
        categoryRepository.insert(title)
    }
}