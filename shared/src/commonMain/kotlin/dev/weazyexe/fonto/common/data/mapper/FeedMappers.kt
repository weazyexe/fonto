package dev.weazyexe.fonto.common.data.mapper

import dev.weazyexe.fonto.common.db.FeedDao
import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.model.feed.Feed

internal fun List<FeedDao>.toFeedList() = map { it.toFeed() }

internal fun FeedDao.toFeed(): Feed = Feed(
    id = id,
    title = title,
    link = link,
    icon = icon?.let { LocalImage(it) }
)

internal fun Feed.toDao(): FeedDao = FeedDao(
    id = id,
    title = title,
    link = link,
    icon = icon?.bytes
)