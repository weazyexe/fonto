package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.mapper.asPosts
import dev.weazyexe.fonto.common.data.repository.NewslineRepository
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Newsline

class GetNewslineUseCase(
    private val newslineRepository: NewslineRepository
) {

    suspend operator fun invoke(feeds: List<Feed>): Newsline {
        val sortedPosts = newslineRepository.getNewslines(feeds)
            .map { it.asPosts() }
            .flatten()
            .sortedByDescending { it.publishedAt }

        return Newsline(posts = sortedPosts)
    }
}