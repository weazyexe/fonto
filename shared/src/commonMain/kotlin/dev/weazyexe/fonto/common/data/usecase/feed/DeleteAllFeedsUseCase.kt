package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.NewslineRepository

class DeleteAllFeedsUseCase(
    private val feedRepository: FeedRepository,
    private val newslineRepository: NewslineRepository
) {

    operator fun invoke() {
        feedRepository.deleteAll()
        newslineRepository.deleteAll()
    }
}