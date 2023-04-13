package dev.weazyexe.fonto.common.data.usecase

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.model.feed.Feed

class UpdateFeedUseCase(
    private val feedRepository: FeedRepository
) {

    operator fun invoke(feed: Feed) {
        feedRepository.update(feed)
    }
}