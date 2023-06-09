package dev.weazyexe.fonto.common.data.usecase.newsline

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.data.ResponseError
import dev.weazyexe.fonto.common.data.mapper.toPosts
import dev.weazyexe.fonto.common.data.repository.AtomRepository
import dev.weazyexe.fonto.common.data.repository.FeedRepository
import dev.weazyexe.fonto.common.data.repository.PostRepository
import dev.weazyexe.fonto.common.data.repository.RssRepository
import dev.weazyexe.fonto.common.feature.debug.VALID_FEED
import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.model.feed.Posts
import dev.weazyexe.fonto.utils.asResponseError
import dev.weazyexe.fonto.utils.flowIo
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class GetPostsUseCase(
    private val feedRepository: FeedRepository,
    private val postRepository: PostRepository,
    private val rssRepository: RssRepository,
    private val atomRepository: AtomRepository
) {

    operator fun invoke(
        limit: Int,
        offset: Int,
        useCache: Boolean,
        shouldShowLoading: Boolean = true,
        useMockFeeds: Boolean = false
    ): Flow<AsyncResult<Posts>> = flowIo {
        if (shouldShowLoading) {
            emit(AsyncResult.Loading())
        }

        addMockFeedsIfNeed(useMockFeeds)

        emit(
            if (useCache) {
                loadPostsFromCache(limit, offset)
            } else {
                loadPostsFromInternet(limit, offset)
            }
        )
    }.catch { emit(AsyncResult.Error(it.asResponseError())) }

    private suspend fun loadPostsFromInternet(limit: Int, offset: Int): AsyncResult<Posts> {
        val feeds = feedRepository.getAll()
        val parsedFeed = getPostsFromFeeds(feeds)

        val loadedPosts = flattenParsedPosts(parsedFeed)
        loadedPosts.posts.forEach { postRepository.insertOrIgnore(it) }

        val areAllFeedsFailed = loadedPosts.loadedWithError.size == feeds.size
        return when {
            feeds.isEmpty() -> AsyncResult.Success(Posts.EMPTY)
            areAllFeedsFailed -> AsyncResult.Error(ResponseError.UnknownError)
            else -> AsyncResult.Success(
                Posts(
                    posts = postRepository.getPosts(
                        limit = limit,
                        offset = offset
                    ),
                    loadedWithError = loadedPosts.loadedWithError
                )
            )
        }
    }

    private suspend fun loadPostsFromCache(limit: Int, offset: Int): AsyncResult<Posts> {
        return AsyncResult.Success(
            Posts(
                posts = postRepository.getPosts(limit = limit, offset = offset),
                loadedWithError = emptyList()
            )
        )
    }

    private fun addMockFeedsIfNeed(useMockFeeds: Boolean) {
        if (useMockFeeds) {
            VALID_FEED.forEach {
                feedRepository.insertOrIgnore(it)
            }
        }
    }

    private suspend fun getPostsFromFeeds(feeds: List<Feed>): List<ParsedFeed> =
        coroutineScope {
            feeds.map {
                async {
                    when (it.type) {
                        Feed.Type.RSS -> rssRepository.getRssFeed(it)
                        Feed.Type.ATOM -> atomRepository.getAtomFeed(it)
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