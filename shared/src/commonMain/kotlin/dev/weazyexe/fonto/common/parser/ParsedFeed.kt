package dev.weazyexe.fonto.common.parser

import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.model.feed.Feed

sealed interface ParsedFeed {

    data class Success(
        val id: Long,
        val title: String,
        val link: String,
        val description: String,
        val posts: List<ParsedPost>,
        val icon: LocalImage?
    ) : ParsedFeed

    data class Error(
        val feed: Feed,
        val throwable: Throwable
    ) : ParsedFeed
}