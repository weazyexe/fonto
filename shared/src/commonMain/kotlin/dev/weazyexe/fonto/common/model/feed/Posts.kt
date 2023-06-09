package dev.weazyexe.fonto.common.model.feed

import dev.weazyexe.fonto.common.feature.parser.ParsedFeed

data class Posts(
    val posts: List<Post>,
    val loadedWithError: List<ParsedFeed.Error>
) : List<Post> by posts {

    companion object {
        val EMPTY = Posts(emptyList(), emptyList())
    }
}