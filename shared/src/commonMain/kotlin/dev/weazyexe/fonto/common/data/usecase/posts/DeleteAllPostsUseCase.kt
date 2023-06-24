package dev.weazyexe.fonto.common.data.usecase.posts

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

internal class DeleteAllPostsUseCase(
    private val postRepository: PostRepository
) {

    operator fun invoke(): Flow<AsyncResult<Unit>> = flowIo {
        emit(AsyncResult.Loading())
        postRepository.deleteAll()
        emit(AsyncResult.Success(Unit))
    }
}