package dev.weazyexe.fonto.common.parser

import com.prof.rssparser.Parser
import dev.weazyexe.fonto.common.error.RssParseException
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.utils.filterHtmlTags
import dev.weazyexe.fonto.common.utils.parseDateTime

actual class RssParser {

    private val parser = Parser.Builder()
        .charset(Charsets.UTF_8)
        .build()

    actual suspend fun parse(feed: Feed): RssFeed {
        try {
            val channel = parser.getChannel(feed.link)
            return RssFeed(
                id = feed.id,
                title = feed.title,
                link = channel.link.orEmpty(),
                description = channel.link.orEmpty(),
                posts = channel.articles.map { article ->
                    RssPost(
                        title = article.title.orEmpty(),
                        link = article.link.orEmpty(),
                        description = article.description.orEmpty().filterHtmlTags().trim(),
                        content = article.content,
                        pubDate = article.pubDate?.parseDateTime(),
                        imageUrl = article.image
                    )
                },
                icon = feed.icon
            )
        } catch (e: Exception) {
            throw RssParseException(e)
        }
    }
}