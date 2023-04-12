package dev.weazyexe.fonto.common.data.usecase

import dev.weazyexe.fonto.common.data.repository.FeedRepository

class CreateFeedUseCase(
    private val feedRepository: FeedRepository
) {

    operator fun invoke(
        title: String,
        link: String
    ) {
        // TODO
        //  1. Load favicon by link hostname
        //  2. Pass all the data to database
        feedRepository.insert(title, link, null)
    }
}