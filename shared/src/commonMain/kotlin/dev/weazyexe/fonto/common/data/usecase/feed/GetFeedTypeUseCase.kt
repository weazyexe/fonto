package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.usecase.atom.IsAtomValidUseCase
import dev.weazyexe.fonto.common.data.usecase.rss.IsRssValidUseCase
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.utils.flowIo
import kotlinx.coroutines.flow.Flow

class GetFeedTypeUseCase(
    private val isAtomValid: IsAtomValidUseCase,
    private val isRssValid: IsRssValidUseCase
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

        throw IllegalArgumentException("Feed type is not supported")
    }
}
