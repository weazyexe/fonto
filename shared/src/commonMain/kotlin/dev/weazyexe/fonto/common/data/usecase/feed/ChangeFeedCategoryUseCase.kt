package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.repository.CategoryRepository
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed

class ChangeFeedCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {

    operator fun invoke(feed: Feed, category: Category) {

    }
}