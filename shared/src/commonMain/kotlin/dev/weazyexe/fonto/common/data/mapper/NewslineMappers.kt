package dev.weazyexe.fonto.common.data.mapper

import dev.weazyexe.fonto.common.db.PostDao
import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.feed.Post
import kotlinx.datetime.Instant
import java.util.UUID

fun ParsedFeed.Success.toPosts(): List<Post> =
    posts.map {
        Post(
            id = generateId(),
            title = it.title,
            description = it.description,
            content = it.content,
            imageUrl = it.imageUrl,
            publishedAt = it.pubDate,
            feed = it.feed,
            isSaved = false,
            link = it.link,
            isRead = false
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
        link = link,
        isRead = isRead.toString()
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
        link = link,
        isRead = isRead.toBooleanStrict()
    )

private fun generateId(): Post.Id =
    Post.Id(UUID.randomUUID().toString())
