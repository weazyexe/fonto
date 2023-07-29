package dev.weazyexe.fonto.common.data.usecase.posts

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.mapper.toPosts
import dev.weazyexe.fonto.common.data.repository.AtomRepository
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.JsonFeedRepository
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.data.repository.RssRepository
import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.utils.extensions.asResponseError
import io.github.aakira.napier.Napier
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

typealias FailedFeeds = List<Feed>

internal class SyncPostsUseCase(
    private val feedRepository: FeedRepository,
    private val postRepository: PostRepository,
    private val rssRepository: RssRepository,
    private val atomRepository: AtomRepository,
    private val jsonFeedRepository: JsonFeedRepository
) {

    suspend operator fun invoke(): AsyncResult<FailedFeeds> {
        val feeds = feedRepository.getAll()
        val parsedFeed = getPostsFromFeeds(feeds)

        val loadedPosts = flattenParsedPosts(parsedFeed)
        loadedPosts.posts.forEach { postRepository.insertOrIgnore(it) }

        val areAllFeedsFailed = loadedPosts.loadedWithError.size == feeds.size

        return when {
            feeds.isEmpty() -> AsyncResult.Success(emptyList())

            areAllFeedsFailed -> AsyncResult.Error(
                loadedPosts.loadedWithError.first().throwable.asResponseError()
            )

            else -> AsyncResult.Success(loadedPosts.loadedWithError.map { it.feed })
        }
    }

    private suspend fun getPostsFromFeeds(feeds: List<Feed>): List<ParsedFeed> =
        coroutineScope {
            feeds.map {
                async {
                    when (it.type) {
                        Feed.Type.RSS -> rssRepository.getRssFeed(it)
                        Feed.Type.ATOM -> atomRepository.getAtomFeed(it)
                        Feed.Type.JSON_FEED -> jsonFeedRepository.getJsonFeed(it)
                    }
                }
            }.awaitAll()
        }

    private fun flattenParsedPosts(feeds: List<ParsedFeed>): LoadedPosts {
        val problematicFeedList = mutableListOf<ParsedFeed.Error>()
        val posts = feeds.flatMap {
            when (it) {
                is ParsedFeed.Success -> it.toPosts()
                is ParsedFeed.Error -> {
                    problematicFeedList.add(it)
                    Napier.e(it.throwable) { "Failed to load a feed ${it.feed.title} from ${it.feed.link}" }
                    emptyList()
                }
            }
        }

        return LoadedPosts(
            posts = posts,
            loadedWithError = problematicFeedList
        )
    }

    private data class LoadedPosts(
        val posts: List<Post>,
        val loadedWithError: List<ParsedFeed.Error>
    )
}