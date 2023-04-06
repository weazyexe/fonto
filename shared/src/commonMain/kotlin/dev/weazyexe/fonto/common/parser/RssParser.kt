package dev.weazyexe.fonto.common.parser

internal expect class RssParser {

    suspend fun parse(url: String): RssFeed
}
