package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.usecase.atom.IsAtomValidUseCase
import dev.weazyexe.fonto.common.data.usecase.jsonfeed.IsJsonFeedValidUseCase
import dev.weazyexe.fonto.common.data.usecase.rss.IsRssValidUseCase
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

internal class GetFeedTypeUseCase(
    private val isAtomValid: IsAtomValidUseCase,
    private val isRssValid: IsRssValidUseCase,
    private val isJsonFeedValid: IsJsonFeedValidUseCase
) {

    operator fun invoke(url: String): Flow<AsyncResult<Feed.Type>> = flowIo {
        emit(AsyncResult.Loading())

        if (isRssValid(url)) {
            emit(AsyncResult.Success(Feed.Type.RSS))
            return@flowIo
        }
        if (isAtomValid(url)) {
            emit(AsyncResult.Success(Feed.Type.ATOM))
            return@flowIo
        }
        if (isJsonFeedValid(url)) {
            emit(AsyncResult.Success(Feed.Type.JSON_FEED))
            return@flowIo
        }

        throw IllegalArgumentException("Feed type is not supported")
    }
}
