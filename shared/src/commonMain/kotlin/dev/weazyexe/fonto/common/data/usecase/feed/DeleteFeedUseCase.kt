package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.NewslineRepository
import dev.weazyexe.fonto.common.model.feed.Feed

class DeleteFeedUseCase(
    private val feedRepository: FeedRepository,
    private val newslineRepository: NewslineRepository
) {

    operator fun invoke(id: Feed.Id) {
        feedRepository.delete(id)
        newslineRepository.deletePostsFromFeed(id.origin)
    }
}
