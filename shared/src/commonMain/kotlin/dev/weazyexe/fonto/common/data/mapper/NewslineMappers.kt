package dev.weazyexe.fonto.common.data.mapper

import dev.weazyexe.fonto.common.db.PostDao
import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.parser.RssFeed
import kotlinx.datetime.toInstant

fun RssFeed.toPosts(): List<Post> =
    posts.map {
        Post(
            id = generateId(id, it.link),
            title = it.title,
            description = it.description,
            content = it.content,
            imageUrl = it.imageUrl,
            publishedAt = it.pubDate,
            source = title,
            sourceIcon = icon,
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
        publishedAt = publishedAt?.toString(),
        source = source,
        sourceIcon = sourceIcon?.bytes,
        isSaved = isSaved.toString()
    )

fun List<PostDao>.toPostList(): List<Post> = map { it.toPost() }

fun PostDao.toPost(): Post =
    Post(
        id = id,
        title = title,
        description = description,
        content = content,
        imageUrl = imageUrl,
        publishedAt = publishedAt?.toInstant(),
        source = source,
        sourceIcon = sourceIcon?.let { LocalImage(it) },
        isSaved = isSaved.toBooleanStrict()
    )

private fun generateId(feedId: Long, postId: String): String =
    "SOURCE:$feedId|LINK:$postId"