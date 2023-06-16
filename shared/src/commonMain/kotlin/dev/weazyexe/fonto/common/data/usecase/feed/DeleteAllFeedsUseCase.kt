package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

class DeleteAllFeedsUseCase(
    private val feedRepository: FeedRepository,
    private val postRepository: PostRepository
) {

    operator fun invoke(): Flow<AsyncResult<Unit>> = flowIo {
        emit(AsyncResult.Loading())
        feedRepository.deleteAll()
        postRepository.deleteAll()
        emit(AsyncResult.Success(Unit))
    }
}