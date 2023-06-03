package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.model.feed.Feed

class GetFeedUseCase(
    private val feedRepository: FeedRepository
) {
    operator fun invoke(id: Feed.Id): Feed = feedRepository.getById(id)
}