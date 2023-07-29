package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.model.feed.Feed

internal class GetFeedsWithNotificationsUseCase(
    private val feedRepository: FeedRepository
) {

    suspend operator fun invoke(): List<Feed> {
        return feedRepository.getAll().filter { it.areNotificationsEnabled }
    }
}