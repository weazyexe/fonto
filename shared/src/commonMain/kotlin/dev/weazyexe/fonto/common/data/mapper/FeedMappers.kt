package dev.weazyexe.fonto.common.data.mapper

import dev.weazyexe.fonto.common.db.FeedDao
import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.model.feed.Category
import dev.weazyexe.fonto.common.model.feed.Feed

internal fun FeedDao.toFeed(category: Category?): Feed = Feed(
    id = Feed.Id(id),
    title = title,
    link = link,
    icon = icon?.let { LocalImage(it) },
    type = Feed.Type.byId(type),
    category = category,
    areNotificationsEnabled = areNotificationsEnabled.toBooleanStrict()
)

internal fun Feed.toDao(): FeedDao = FeedDao(
    id = id.origin,
    title = title,
    link = link,
    icon = icon?.bytes,
    type = type.id,
    categoryId = category?.id?.origin,
    areNotificationsEnabled = areNotificationsEnabled.toString()
)
