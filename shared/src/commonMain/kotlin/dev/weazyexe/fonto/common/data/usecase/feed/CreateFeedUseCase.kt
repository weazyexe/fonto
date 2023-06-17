package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

internal class CreateFeedUseCase(
    private val feedRepository: FeedRepository
) {

    operator fun invoke(
        title: String,
        link: String,
        image: LocalImage?,
        type: Feed.Type,
        category: Category?
    ): Flow<AsyncResult<Unit>> = flowIo {
        emit(AsyncResult.Loading())
        feedRepository.insert(title, link, image, type, category?.id)
        emit(AsyncResult.Success(Unit))
    }
}