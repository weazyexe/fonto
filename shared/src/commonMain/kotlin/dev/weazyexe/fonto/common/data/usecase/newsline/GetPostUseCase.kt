package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.utils.asResponseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetPostUseCase(
    private val postRepository: PostRepository
) {

    operator fun invoke(
        postId: Post.Id
    ): Flow<AsyncResult<Post>> = flow {
        emit(AsyncResult.Loading())
        emit(AsyncResult.Success(postRepository.getPostById(postId)))
    }.catch { emit(AsyncResult.Error(it.asResponseError())) }
}
