package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

class UpdateFeedUseCase(
    private val feedRepository: FeedRepository
) {

    operator fun invoke(feed: Feed): Flow<AsyncResult<Unit>> = flowIo {
        emit(AsyncResult.Loading())
        feedRepository.update(feed)
        emit(AsyncResult.Success(Unit))
    }
}