package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.utils.flowIo
import kotlinx.coroutines.flow.Flow

class GetFiltersUseCase(private val postRepository: PostRepository) {

    operator fun invoke(): Flow<AsyncResult<List<NewslineFilter>>> = flowIo {
        emit(AsyncResult.Loading())
        emit(AsyncResult.Success(postRepository.composeFilters()))
    }
}