package dev.weazyexe.fonto.common.data.usecase.posts

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

internal class UpdatePostUseCase(
    private val postRepository: PostRepository
) {

    operator fun invoke(post: Post): Flow<AsyncResult<Post>> = flowIo {
        postRepository.insertOrUpdate(post)
        emit(AsyncResult.Success(post))
    }
}