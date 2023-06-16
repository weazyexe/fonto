package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

class GetPostUseCase(
    private val postRepository: PostRepository
) {

    operator fun invoke(postId: Post.Id): Flow<AsyncResult<Post>> = flowIo {
        emit(AsyncResult.Loading())
        emit(AsyncResult.Success(postRepository.getPostById(postId)))
    }
}
