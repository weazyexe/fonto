package dev.weazyexe.fonto.common.data.usecase

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.model.feed.Feed

class GetFeedUseCase(
    private val feedRepository: FeedRepository
) {

    suspend operator fun invoke(): List<Feed> = feedRepository.getAll()
}