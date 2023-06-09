package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.utils.asResponseError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UpdatePostUseCase(
    private val postRepository: PostRepository
) {

    operator fun invoke(post: Post): Flow<AsyncResult<Post>> = flow<AsyncResult<Post>> {
        postRepository.insertOrUpdate(post)
        emit(AsyncResult.Success(post))
    }
        .catch { emit(AsyncResult.Error(it.asResponseError())) }
        .flowOn(Dispatchers.IO)
}