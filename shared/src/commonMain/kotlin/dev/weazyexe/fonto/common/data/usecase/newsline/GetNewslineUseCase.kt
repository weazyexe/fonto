package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.mapper.toPosts
import dev.weazyexe.fonto.common.data.repository.AtomRepository
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.NewslineRepository
import dev.weazyexe.fonto.common.data.repository.RssRepository
import dev.weazyexe.fonto.common.feature.debug.VALID_FEED
import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Newsline
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class GetNewslineUseCase(
    private val feedRepository: FeedRepository,
    private val newslineRepository: NewslineRepository,
    private val rssRepository: RssRepository,
    private val atomRepository: AtomRepository
) {

    suspend operator fun invoke(
        filters: List<NewslineFilter>? = null,
        useCache: Boolean = false,
        useMockFeeds: Boolean = false
    ): Newsline {
        return if (useCache) {
            getNewslineFromCache(filters)
        } else {
            getNewslineFromServer(filters, useMockFeeds)
        }
    }

    private suspend fun getNewslineFromServer(
        filters: List<NewslineFilter>?,
        useMockFeeds: Boolean
    ): Newsline {
        if (useMockFeeds) {
            VALID_FEED.forEach {
                feedRepository.insertOrIgnore(it)
            }
        }
        val feeds = feedRepository.getAll()

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
            .flatMap {
                when (it) {
                    is ParsedFeed.Success -> it.toPosts()
                    is ParsedFeed.Error -> {
                        problematicFeedList.add(it)
                        emptyList()
                    }
                }
            }
            .forEach { newslineRepository.insertOrIgnore(it) }

        val filtersToUse = filters ?: newslineRepository.composeFilters()
        return if (feeds.isEmpty() || problematicFeedList.size != feeds.size) {
            Newsline.Success(
                posts = newslineRepository.getAll(
                    limit = 20,
                    offset = 0,
                    filters = filtersToUse
                ),
                loadedWithError = problematicFeedList,
                filters = filtersToUse
            )
        } else {
            Newsline.Error
        }
    }

    private suspend fun getNewslineFromCache(
        filters: List<NewslineFilter>?
    ): Newsline {
        val filtersToUse = filters ?: newslineRepository.composeFilters()
        return Newsline.Success(
            posts = newslineRepository.getAll(
                limit = 20,
                offset = 0,
                filters = filtersToUse
            ),
            loadedWithError = emptyList(),
            filters = filtersToUse
        )
    }
}