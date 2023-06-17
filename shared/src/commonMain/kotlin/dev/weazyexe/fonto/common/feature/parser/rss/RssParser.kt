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
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.serialization.UnknownChildHandler
import nl.adaptivity.xmlutil.serialization.XML

internal class RssParser(private val httpClient: HttpClient) {

    @OptIn(ExperimentalXmlUtilApi::class)
    private val xml = XML {
        defaultPolicy {
            unknownChildHandler = UnknownChildHandler { _, _, _, _, _ -> emptyList() }
        }
    }

    suspend fun parse(feed: Feed): ParsedFeed {
        return try {
            val rawRssXml = httpClient.get(feed.link).bodyAsText()
            val rss = xml.decodeFromString(RssRoot.serializer(), rawRssXml)
            ParsedFeed.Success(
                id = feed.id.origin,
                title = feed.title,
                link = rss.channel.link,
                description = rss.channel.link,
                posts = rss.channel.items.map { item ->
                    ParsedPost(
                        title = item.title.orEmpty().cleanUpText(),
                        link = item.link.orEmpty().replaceHttp(),
                        description = item.description.orEmpty().cleanUpText(),
                        pubDate = item.pubDate?.parseDateTime() ?: Clock.System.now(),
                        imageUrl = item.imageUrl ?: item.description.orEmpty().getFirstImageUrlFromHtml(),
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