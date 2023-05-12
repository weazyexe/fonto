package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.mapper.toPosts
import dev.weazyexe.fonto.common.data.repository.AtomRepository
import dev.weazyexe.fonto.common.data.repository.NewslineRepository
import dev.weazyexe.fonto.common.data.repository.RssRepository
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Newsline
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class GetNewslineUseCase(
    private val newslineRepository: NewslineRepository,
    private val rssRepository: RssRepository,
    private val atomRepository: AtomRepository
) {

    suspend operator fun invoke(
        feeds: List<Feed>,
        filters: List<NewslineFilter>? = null
    ): Newsline {
        val parsedFeed = coroutineScope {
            feeds.map {
                async {
                    when (it.type) {
                        Feed.Type.RSS -> rssRepository.getRssFeed(it)
                        Feed.Type.ATOM -> atomRepository.getAtomFeed(it)
                    }
                }
            }.awaitAll()
        }

        val problematicFeedList = mutableListOf<ParsedFeed.Error>()
        parsedFeed
            .map {
                when (it) {
                    is ParsedFeed.Success -> it.toPosts()
                    is ParsedFeed.Error -> {
                        problematicFeedList.add(it)
                        emptyList()
                    }
                }
            }
            .flatten()
            .forEach { newslineRepository.insertOrUpdate(it) }

        val filtersToUse = filters ?: newslineRepository.getDefaultFilters()
        return Newsline(
            posts = newslineRepository.getAll(
                feeds = feeds,
                limit = 20,
                offset = 0,
                filters = filtersToUse
            ),
            loadedWithError = problematicFeedList,
            filters = filtersToUse
        )
    }
}