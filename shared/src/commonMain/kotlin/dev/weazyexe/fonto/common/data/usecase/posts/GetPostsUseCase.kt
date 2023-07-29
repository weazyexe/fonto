package dev.weazyexe.fonto.common.data.usecase.posts

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

internal class GetPostsUseCase(
    private val postRepository: PostRepository,
    private val syncPosts: SyncPostsUseCase
) {

    operator fun invoke(
        limit: Int,
        offset: Int,
        useCache: Boolean,
        shouldShowLoading: Boolean = true
    ): Flow<AsyncResult<List<Post>>> = flowIo {
        if (shouldShowLoading) {
            emit(AsyncResult.Loading())
        }

        if (!useCache) {
            syncPosts()
        }

        emit(
            AsyncResult.Success(
                data = postRepository.getPosts(limit = limit, offset = offset)
            )
        )
    }
}