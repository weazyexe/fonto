package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.repository.NewslineRepository
import dev.weazyexe.fonto.common.model.feed.Feed

class IsNewslineValidUseCase(private val newslineRepository: NewslineRepository) {

    suspend operator fun invoke(url: String): Boolean {
        val feed = Feed(id = 0, title = "", link = url, icon = null)
        return newslineRepository.getNewsline(feed).posts.isNotEmpty()
    }
}