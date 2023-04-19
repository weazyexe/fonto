package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.repository.NewslineRepository
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Newsline

class GetCachedNewslineUseCase(
    private val newslineRepository: NewslineRepository
) {

    suspend operator fun invoke(feeds: List<Feed>): Newsline =
        Newsline(
            posts = newslineRepository.getAll(feeds),
            loadedWithError = emptyList()
        )
}