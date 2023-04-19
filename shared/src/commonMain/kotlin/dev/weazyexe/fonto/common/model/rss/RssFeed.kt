package dev.weazyexe.fonto.common.model.rss

import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.model.feed.Feed

sealed interface RssFeed {

    data class Success(
        val id: Long,
        val title: String,
        val link: String,
        val description: String,
        val posts: List<RssPost>,
        val icon: LocalImage?
    ): RssFeed

    data class Error(
        val feed: Feed,
        val throwable: Throwable
    ) : RssFeed
}