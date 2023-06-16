package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

class GetFeedUseCase(
    private val feedRepository: FeedRepository
) {
    operator fun invoke(id: Feed.Id): Flow<AsyncResult<Feed>> = flowIo {
        emit(AsyncResult.Loading())
        emit(AsyncResult.Success(feedRepository.getById(id)))
    }
}