package dev.weazyexe.fonto.common.parser.rss

import com.prof.rssparser.Parser
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.parser.ParsedFeed
import dev.weazyexe.fonto.common.parser.ParsedPost
import dev.weazyexe.fonto.common.utils.cleanUpText
import dev.weazyexe.fonto.common.utils.getFirstImageUrlFromHtml
import dev.weazyexe.fonto.common.utils.parseDateTime
import dev.weazyexe.fonto.common.utils.replaceHttp
import kotlinx.datetime.Clock

actual class RssParser {

    private val parser = Parser.Builder()
        .charset(Charsets.UTF_8)
        .build()

    actual suspend fun parse(feed: Feed): ParsedFeed {
        try {
            val channel = parser.getChannel(feed.link)
            return ParsedFeed.Success(
                id = feed.id.origin,
                title = feed.title,
                link = channel.link.orEmpty(),
                description = channel.link.orEmpty(),
                posts = channel.articles.map { article ->
                    ParsedPost(
                        title = article.title.orEmpty(),
                        link = article.link.orEmpty().replaceHttp(),
                        description = article.description.orEmpty().cleanUpText(),
                        content = article.content,
                        pubDate = article.pubDate?.parseDateTime() ?: Clock.System.now(),
                        imageUrl = article.image
                            ?: article.description?.getFirstImageUrlFromHtml()
                            ?: article.content?.getFirstImageUrlFromHtml(),
                        feed = feed
                    )
                },
                icon = feed.icon
            )
        } catch (e: Exception) {
            return ParsedFeed.Error(feed, e)
        }
    }
}