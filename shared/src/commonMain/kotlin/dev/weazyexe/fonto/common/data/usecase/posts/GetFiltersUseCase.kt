package dev.weazyexe.fonto.common.data.usecase.posts

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.feature.posts.PostsFilter
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

internal class GetFiltersUseCase(private val postRepository: PostRepository) {

    operator fun invoke(): Flow<AsyncResult<List<PostsFilter>>> = flowIo {
        emit(AsyncResult.Loading())
        emit(AsyncResult.Success(postRepository.composeFilters()))
    }
}