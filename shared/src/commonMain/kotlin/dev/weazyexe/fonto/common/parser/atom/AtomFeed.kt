package dev.weazyexe.fonto.common.parser.atom

import dev.weazyexe.fonto.common.model.base.LocalImage
import dev.weazyexe.fonto.common.model.feed.Feed

sealed interface AtomFeed {

    data class Success(
        val id: Long,
        val title: String,
        val link: String,
        val description: String,
        val posts: List<AtomPost>,
        val icon: LocalImage?
    ): AtomFeed

    data class Error(
        val feed: Feed,
        val throwable: Throwable
    ) : AtomFeed
}