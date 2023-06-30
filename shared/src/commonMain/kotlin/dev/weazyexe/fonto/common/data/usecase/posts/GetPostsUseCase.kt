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
import dev.weazyexe.fonto.common.model.feed.Posts
import dev.weazyexe.fonto.utils.extensions.asResponseError
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn

internal class GetPostsUseCase(
    private val feedRepository: FeedRepository,
    private val postRepository: PostRepository,
    private val rssRepository: RssRepository,
    private val atomRepository: AtomRepository,
    private val jsonFeedRepository: JsonFeedRepository
) {

    operator fun invoke(
        limit: Int,
        offset: Int,
        useCache: Boolean,
        shouldShowLoading: Boolean = true
    ): Flow<AsyncResult<Posts>> = channelFlow {
        if (shouldShowLoading) {
            send(AsyncResult.Loading())
        }

        if (useCache) {
            send(loadPostsFromCache(limit, offset))
        } else {
            loadPostsFromInternet(limit, offset)
        }
    }
        .flowOn(Dispatchers.IO)
        .catch {
            Napier.e(throwable = it, message = "Error in channelFlow")
            emit(AsyncResult.Error(it.asResponseError()))
        }

    private suspend fun ProducerScope<AsyncResult<Posts>>.loadPostsFromInternet(
        limit: Int,
        offset: Int
    ) {
        val feeds = feedRepository.getAll()
        var loadedPostsCount = 0

        val parsedFeed = coroutineScope {
            feeds
                .map {
                    async {
                        when (it.type) {
                            Feed.Type.RSS -> rssRepository.getRssFeed(it)
                            Feed.Type.ATOM -> atomRepository.getAtomFeed(it)
                            Feed.Type.JSON_FEED -> jsonFeedRepository.getJsonFeed(it)
                        }.also {
                            send(
                                AsyncResult.Loading.Progress(
                                    current = ++loadedPostsCount,
                                    total = feeds.size
                                )
                            )
                        }
                    }
                }
                .awaitAll()
        }

        val loadedPosts = flattenParsedPosts(parsedFeed)
        loadedPosts.posts.forEach { postRepository.insertOrIgnore(it) }

        val areAllFeedsFailed = loadedPosts.loadedWithError.size == feeds.size
        send(
            when {
                feeds.isEmpty() -> AsyncResult.Success(Posts.EMPTY)
                areAllFeedsFailed -> AsyncResult.Error(
                    loadedPosts.loadedWithError.first().throwable.asResponseError()
                )

                else -> {
                    val posts = postRepository.getPosts(
                        limit = limit,
                        offset = offset
                    )
                    AsyncResult.Success(Posts(posts = posts))
                }
            }
        )
    }

    private suspend fun loadPostsFromCache(limit: Int, offset: Int): AsyncResult<Posts> {
        return AsyncResult.Success(
            Posts(
                posts = postRepository.getPosts(limit = limit, offset = offset)
            )
        )
    }

    private fun flattenParsedPosts(feeds: List<ParsedFeed>): LoadedPosts {
        val problematicFeedList = mutableListOf<ParsedFeed.Error>()
        val posts = feeds.flatMap {
            when (it) {
                is ParsedFeed.Success -> it.toPosts()
                is ParsedFeed.Error -> {
                    problematicFeedList.add(it)
                    Napier.e(it.throwable) { "Failed to load a feed from ${it.feed.link}" }
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