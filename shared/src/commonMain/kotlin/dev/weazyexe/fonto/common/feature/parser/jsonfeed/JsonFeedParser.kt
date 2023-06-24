package dev.weazyexe.fonto.common.feature.parser.jsonfeed

import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.feature.parser.ParsedPost
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.utils.bruteForceParseDateTime
import dev.weazyexe.fonto.common.utils.cleanUpText
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json

internal class JsonFeedParser(
    private val json: Json,
    private val httpClient: HttpClient
) {

    suspend fun parse(feed: Feed): ParsedFeed {
        return try {
            val rawJsonFeed = httpClient.get(feed.link).bodyAsText()
            val jsonFeed = json.decodeFromString(JsonFeedRoot.serializer(), rawJsonFeed)

            ParsedFeed.Success(
                id = feed.id.origin,
                title = feed.title,
                link = jsonFeed.link,
                description = jsonFeed.description.orEmpty(),
                posts = jsonFeed.items.map { item ->
                    val id = item.id
                    val title = item.title.orEmpty()
                    val link = item.url
                    val description = item.summary
                        ?: item.contentText
                        ?: item.contentHtml?.cleanUpText()
                        ?: ""
                    val pubDate = (item.datePublished ?: item.dateModified)
                        ?.bruteForceParseDateTime() ?: Clock.System.now()
                    val imageUrl = item.image ?: item.bannerImage

                    ParsedPost(
                        id = id,
                        title = title,
                        link = link,
                        description = description,
                        pubDate = pubDate,
                        imageUrl = imageUrl,
                        feed = feed
                    )
                },
                icon = feed.icon
            )
        } catch (e: Exception) {
            Napier.e(e) { "JSON Feed parser error" }
            return ParsedFeed.Error(feed, e)
        }
    }
}