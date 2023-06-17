package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

internal class GetAllFeedsUseCase(
    private val feedRepository: FeedRepository
) {

    operator fun invoke(): Flow<AsyncResult<List<Feed>>> = flowIo {
        emit(AsyncResult.Loading())
        emit(AsyncResult.Success(feedRepository.getAll()))
    }
}