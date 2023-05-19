package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed

class ChangeFeedCategoryUseCase(
    private val feedRepository: FeedRepository
) {

    operator fun invoke(feed: Feed, category: Category?) {
        feedRepository.update(feed.copy(category = category))
    }
}