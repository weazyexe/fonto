package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.model.feed.Feed

class DeleteFeedUseCase(
    private val feedRepository: FeedRepository,
    private val postRepository: PostRepository
) {

    operator fun invoke(id: Feed.Id) {
        feedRepository.delete(id)
        postRepository.deletePostsFromFeed(id.origin)
    }
}
