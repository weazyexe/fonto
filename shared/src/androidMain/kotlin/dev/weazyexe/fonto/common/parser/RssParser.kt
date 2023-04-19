package dev.weazyexe.fonto.common.parser

import com.prof.rssparser.Parser
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.model.rss.RssFeed
import dev.weazyexe.fonto.common.model.rss.RssPost
import dev.weazyexe.fonto.common.utils.cleanUpText
import dev.weazyexe.fonto.common.utils.parseDateTime

actual class RssParser {

    private val parser = Parser.Builder()
        .charset(Charsets.UTF_8)
        .build()

    actual suspend fun parse(feed: Feed): RssFeed {
        try {
            val channel = parser.getChannel(feed.link)
            return RssFeed.Success(
                id = feed.id,
                title = feed.title,
                link = channel.link.orEmpty(),
                description = channel.link.orEmpty(),
                posts = channel.articles.map { article ->
                    RssPost(
                        title = article.title.orEmpty(),
                        link = article.link.orEmpty(),
                        description = article.description.orEmpty().cleanUpText().trim(),
                        content = article.content,
                        pubDate = article.pubDate?.parseDateTime(),
                        imageUrl = article.image,
                        feed = feed
                    )
                },
                icon = feed.icon
            )
        } catch (e: Exception) {
            return RssFeed.Error(feed, e)
        }
    }
}