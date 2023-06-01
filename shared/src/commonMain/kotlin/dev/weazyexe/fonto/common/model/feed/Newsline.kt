package dev.weazyexe.fonto.common.model.feed

import dev.weazyexe.fonto.common.feature.parser.ParsedFeed

sealed interface Newsline {

    data class Success(
        val posts: List<Post>,
        val loadedWithError: List<ParsedFeed.Error>
    ) : Newsline

    object Error : Newsline
}