package dev.weazyexe.fonto.common.data.usecase.category

import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.common.model.feed.Category

class GetAllCategoriesUseCase(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke(): List<Category> = categoryRepository.getAll()
}