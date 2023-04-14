package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.model.base.LocalImage

class CreateFeedUseCase(
    private val feedRepository: FeedRepository
) {

    operator fun invoke(
        title: String,
        link: String,
        image: LocalImage?
    ) {
        feedRepository.insert(title, link, image)
    }
}