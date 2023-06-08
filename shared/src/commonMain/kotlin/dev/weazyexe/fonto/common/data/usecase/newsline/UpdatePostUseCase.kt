package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.utils.asResponseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class UpdatePostUseCase(
    private val postRepository: PostRepository
) {

    operator fun invoke(post: Post): Flow<AsyncResult<Unit>> = flow<AsyncResult<Unit>> {
        postRepository.insertOrUpdate(post)
        emit(AsyncResult.Success(Unit))
    }.catch { emit(AsyncResult.Error(it.asResponseError())) }
}