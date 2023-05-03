package dev.weazyexe.fonto.common.model.feed

import dev.weazyexe.fonto.common.parser.ParsedFeed

data class Newsline(
    val posts: List<Post>,
    val loadedWithError: List<ParsedFeed.Error>
)