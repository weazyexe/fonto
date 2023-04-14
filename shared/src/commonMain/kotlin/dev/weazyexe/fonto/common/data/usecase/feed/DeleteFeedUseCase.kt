package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.repository.FeedRepository

class DeleteFeedUseCase(
    private val feedRepository: FeedRepository
) {

    operator fun invoke(id: Long) {
        feedRepository.delete(id)
    }
}