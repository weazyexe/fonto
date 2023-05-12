package dev.weazyexe.fonto.common.model.feed

import dev.weazyexe.fonto.common.feature.newsline.NewslineFilter
import dev.weazyexe.fonto.common.feature.parser.ParsedFeed

data class Newsline(
    val posts: List<Post>,
    val loadedWithError: List<ParsedFeed.Error>,
    val filters: List<NewslineFilter>
)