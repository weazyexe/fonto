package dev.weazyexe.fonto.common.data.usecase.atom

import dev.weazyexe.fonto.common.data.repository.AtomRepository
import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed

internal class IsAtomValidUseCase(private val atomRepository: AtomRepository) {

    suspend operator fun invoke(url: String): Boolean {
        val feed = Feed(
            id = Feed.Id(0),
            title = "",
            link = url,
            icon = null,
            type = Feed.Type.ATOM,
            category = Category(Category.Id(0), "")
        )
        val parsedFeed = atomRepository.getAtomFeed(feed) as? ParsedFeed.Success ?: return false
        return parsedFeed.posts.isNotEmpty()
    }
}
