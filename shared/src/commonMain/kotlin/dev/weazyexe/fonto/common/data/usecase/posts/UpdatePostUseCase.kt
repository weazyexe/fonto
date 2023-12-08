package dev.weazyexe.fonto.common.data.usecase.posts

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

interface UpdatePostUseCase {

    operator fun invoke(post: Post): Flow<AsyncResult<Post>>
}

internal class UpdatePostUseCaseImpl(
    private val postRepository: PostRepository
) : UpdatePostUseCase {

    override operator fun invoke(post: Post): Flow<AsyncResult<Post>> = flowIo {
        postRepository.insertOrUpdate(post)
        emit(AsyncResult.Success(post))
    }
}
