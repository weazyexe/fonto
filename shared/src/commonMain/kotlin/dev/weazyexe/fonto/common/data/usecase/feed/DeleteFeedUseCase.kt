package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

class DeleteFeedUseCase(
    private val feedRepository: FeedRepository,
    private val postRepository: PostRepository
) {

    operator fun invoke(id: Feed.Id): Flow<AsyncResult<Unit>> = flowIo {
        emit(AsyncResult.Loading())

        feedRepository.delete(id)
        postRepository.deletePostsFromFeed(id.origin)

        emit(AsyncResult.Success(Unit))
    }
}
