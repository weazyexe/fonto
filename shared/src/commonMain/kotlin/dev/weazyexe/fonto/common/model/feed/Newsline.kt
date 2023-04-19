package dev.weazyexe.fonto.common.model.feed

import dev.weazyexe.fonto.common.model.rss.RssFeed

data class Newsline(
    val posts: List<Post>,
    val loadedWithError: List<RssFeed.Error>
)