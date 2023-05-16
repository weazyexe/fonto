package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.model.feed.Feed

class GetAllFeedsUseCase(
    private val feedRepository: FeedRepository
) {

    suspend operator fun invoke(): List<Feed> = feedRepository.getAll()
}