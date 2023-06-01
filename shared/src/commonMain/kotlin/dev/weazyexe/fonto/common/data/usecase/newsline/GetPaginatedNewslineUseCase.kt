package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.repository.NewslineRepository
import dev.weazyexe.fonto.common.model.feed.Newsline

class GetPaginatedNewslineUseCase(
    private val newslineRepository: NewslineRepository
) {

    suspend operator fun invoke(
        limit: Int,
        offset: Int
    ): Newsline {
        return Newsline.Success(
            posts = newslineRepository.getPosts(
                limit = limit,
                offset = offset
            ),
            loadedWithError = emptyList()
        )
    }
}
