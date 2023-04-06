package dev.weazyexe.fonto.common.data.mapper

import dev.weazyexe.fonto.common.domain.Feed
import dev.weazyexe.fonto.common.domain.Post
import dev.weazyexe.fonto.common.domain.RssSource
import dev.weazyexe.fonto.common.parser.RssFeed
import dev.weazyexe.fonto.common.parser.RssPost
import java.util.*

internal fun RssFeed.toFeed(): Feed =
    Feed(
        title = title,
        link = link,
        description = description,
        posts = posts.map { it.toPost(this) }
    )

internal fun RssPost.toPost(feed: RssFeed): Post =
    Post(
        id = hashCode().toString(),
        title = title,
        description = description,
        content = content,
        imageUrl = imageUrl,
        publishedAt = pubDate,
        source = RssSource(
            id = UUID.randomUUID().toString(),
            title = feed.title,
            image = null
        ),
        isSaved = false
    )