package dev.weazyexe.fonto.common.feature.parser.rss

import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.feature.parser.ParsedPost
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.utils.cleanUpText
import dev.weazyexe.fonto.common.utils.getFirstImageUrlFromHtml
import dev.weazyexe.fonto.common.utils.parseDateTime
import dev.weazyexe.fonto.common.utils.replaceHttp
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.datetime.Clock
import nl.adaptivity.xmlutil.serialization.XML

internal class RssParser(
    private val xml: XML,
    private val httpClient: HttpClient
) {

    suspend fun parse(feed: Feed): ParsedFeed {
        return try {
            val rawRssXml = httpClient.get(feed.link).bodyAsText()
            val rss = xml.decodeFromString(RssRoot.serializer(), rawRssXml)

            ParsedFeed.Success(
                id = feed.id.origin,
                title = feed.title,
                link = rss.channel.link,
                description = rss.channel.description,
                posts = rss.channel.items?.map { item ->
                    val title = item.title.orEmpty().cleanUpText()
                    val link = item.link.orEmpty().replaceHttp()
                    val description = item.description.orEmpty().cleanUpText()
                    val pubDate = item.pubDate?.parseDateTime() ?: Clock.System.now()
                    val imageUrl = item.imageUrl ?: item.description.orEmpty()
                        .getFirstImageUrlFromHtml()

                    ParsedPost(
                        id = generateId(feed.id.origin, link),
                        title = title,
                        link = link,
                        description = description,
                        pubDate = pubDate,
                        imageUrl = imageUrl,
                        feed = feed
                    )
                }.orEmpty(),
                icon = feed.icon
            )
        } catch (e: Exception) {
            return ParsedFeed.Error(feed, e)
        }
    }

    private fun generateId(feedId: Long, postId: String): String =
        "SOURCE:$feedId|LINK:$postId".hashCode().toString()
}