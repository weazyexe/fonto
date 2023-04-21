package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.repository.NewslineRepository
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Newsline

class GetPaginatedNewslineUseCase(
    private val newslineRepository: NewslineRepository
) {

    suspend operator fun invoke(feeds: List<Feed>, limit: Int, offset: Int): Newsline {
        return Newsline(
            posts = newslineRepository.getAll(feeds = feeds, limit = limit, offset = offset),
            loadedWithError = emptyList()
        )
    }
}
