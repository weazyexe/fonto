package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.model.feed.Feed

class GetFeedIconUseCase(
    private val feedRepository: FeedRepository
) {

    operator fun invoke(feedId: Feed.Id): LocalImage? {
        val feed = feedRepository.getById(feedId)
        return feed.icon
    }
}
