package dev.weazyexe.fonto.common.data.mapper

import dev.weazyexe.fonto.common.db.PostDao
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.parser.rss.RssFeed
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
            isSaved = false,
            link = it.link
        )
    }

fun Post.toDao(): PostDao =
    PostDao(
        id = id.origin,
        title = title,
        description = description,
        content = content,
        imageUrl = imageUrl,
        publishedAt = publishedAt.epochSeconds,
        feedId = feed.id.origin,
        isSaved = isSaved.toString(),
        link = link
    )

fun PostDao.toPost(feed: Feed): Post =
    Post(
        id = Post.Id(id),
        title = title,
        description = description,
        content = content,
        imageUrl = imageUrl,
        publishedAt = Instant.fromEpochSeconds(publishedAt),
        feed = feed,
        isSaved = isSaved.toBooleanStrict(),
        link = link
    )

private fun generateId(feedId: Long, postId: String): Post.Id =
    Post.Id("SOURCE:$feedId|LINK:$postId")
