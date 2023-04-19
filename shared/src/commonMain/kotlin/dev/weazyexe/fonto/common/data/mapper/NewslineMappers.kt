package dev.weazyexe.fonto.common.data.mapper

import dev.weazyexe.fonto.common.db.PostDao
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.model.rss.RssFeed
import kotlinx.datetime.Instant

fun RssFeed.Success.toPosts(): List<Post> =
    posts.map {
        Post(
            id = generateId(id, it.link),
            title = it.title,
            description = it.description,
            content = it.content,
            imageUrl = it.imageUrl,
            publishedAt = it.pubDate,
            feed = it.feed,
            isSaved = false
        )
    }

fun Post.toDao(): PostDao =
    PostDao(
        id = id,
        title = title,
        description = description,
        content = content,
        imageUrl = imageUrl,
        publishedAt = publishedAt.epochSeconds,
        feedId = feed.id,
        isSaved = isSaved.toString()
    )

fun PostDao.toPost(feed: Feed): Post =
    Post(
        id = id,
        title = title,
        description = description,
        content = content,
        imageUrl = imageUrl,
        publishedAt = Instant.fromEpochSeconds(publishedAt),
        feed = feed,
        isSaved = isSaved.toBooleanStrict()
    )

private fun generateId(feedId: Long, postId: String): String =
    "SOURCE:$feedId|LINK:$postId"