package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.model.base.LocalImage

class GetFeedIconUseCase(
    private val feedRepository: FeedRepository
) {

    operator fun invoke(feedId: Long): LocalImage? {
        val feed = feedRepository.getById(feedId)
        return feed.icon
    }
}