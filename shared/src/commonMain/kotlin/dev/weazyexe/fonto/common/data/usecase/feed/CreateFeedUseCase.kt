package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed

class CreateFeedUseCase(
    private val feedRepository: FeedRepository
) {

    operator fun invoke(
        title: String,
        link: String,
        image: LocalImage?,
        type: Feed.Type
    ) {
        feedRepository.insert(title, link, image, type, Category.Id(0))
    }
}