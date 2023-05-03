package dev.weazyexe.fonto.common.data.usecase.feed

import dev.weazyexe.fonto.common.data.usecase.atom.IsAtomValidUseCase
import dev.weazyexe.fonto.common.data.usecase.rss.IsRssValidUseCase
import dev.weazyexe.fonto.common.model.feed.Feed

class GetFeedTypeUseCase(
    private val isAtomValid: IsAtomValidUseCase,
    private val isRssValid: IsRssValidUseCase
) {

    suspend operator fun invoke(url: String): Feed.Type? {
        if (isRssValid(url)) return Feed.Type.RSS
        if (isAtomValid(url)) return Feed.Type.ATOM

        return null
    }
}
