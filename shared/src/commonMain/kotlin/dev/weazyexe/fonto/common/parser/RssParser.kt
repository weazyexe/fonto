package dev.weazyexe.fonto.common.parser

expect class RssParser {

    suspend fun parse(url: String): RssFeed
}
