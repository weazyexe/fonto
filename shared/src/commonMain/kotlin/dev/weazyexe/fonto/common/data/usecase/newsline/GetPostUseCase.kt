package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.NewslineRepository
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post

class GetPostUseCase(
    private val feedRepository: FeedRepository,
    private val newslineRepository: NewslineRepository
) {

    operator fun invoke(
        postId: Post.Id,
        feedId: Feed.Id
    ): Post {
        val feed = feedRepository.getById(feedId)
        return newslineRepository.getPostById(postId, feed)
    }
}
