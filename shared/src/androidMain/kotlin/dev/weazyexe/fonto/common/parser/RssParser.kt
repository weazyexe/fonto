package dev.weazyexe.fonto.common.parser

import com.prof.rssparser.Parser
import dev.weazyexe.fonto.common.error.RssParseException
import kotlinx.datetime.LocalDateTime

internal actual class RssParser {

    private val parser = Parser.Builder()
        .charset(Charsets.UTF_8)
        .build()

    actual suspend fun parse(url: String): RssFeed {
        try {
            val channel = parser.getChannel(url)
            return RssFeed(
                title = channel.title.orEmpty(),
                link = channel.link.orEmpty(),
                description = channel.link.orEmpty(),
                posts = channel.articles.map { article ->
                    RssPost(
                        title = article.title.orEmpty(),
                        link = article.link.orEmpty(),
                        description = article.description.orEmpty(),
                        content = article.content,
                        pubDate = article.pubDate?.let { LocalDateTime.parse(it) },
                        imageUrl = article.image
                    )
                }
            )
        } catch (e: Exception) {
            throw RssParseException(e)
        }
    }
}