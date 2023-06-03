package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.PostRepository

class DeleteAllFeedsUseCase(
    private val feedRepository: FeedRepository,
    private val postRepository: PostRepository
) {

    operator fun invoke() {
        feedRepository.deleteAll()
        postRepository.deleteAll()
    }
}