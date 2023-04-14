package dev.weazyexe.fonto.common.data.mapper

import dev.weazyexe.fonto.common.model.feed.Post
import dev.weazyexe.fonto.common.parser.RssFeed

fun RssFeed.asPosts(): List<Post> =
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

private fun generateId(feedId: Long, postId: String): String =
    "SOURCE:$feedId|LINK:$postId"