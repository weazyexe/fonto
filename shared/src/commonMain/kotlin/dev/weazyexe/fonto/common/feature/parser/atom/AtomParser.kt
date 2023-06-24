package dev.weazyexe.fonto.common.feature.parser.atom

import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.feature.parser.ParsedPost
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.utils.DATE_TIME_FORMAT_RFC_3339
import dev.weazyexe.fonto.common.utils.cleanUpText
import dev.weazyexe.fonto.common.utils.getFirstImageUrlFromHtml
import dev.weazyexe.fonto.common.utils.parseDateTime
import dev.weazyexe.fonto.common.utils.replaceHttp
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.datetime.Clock
import nl.adaptivity.xmlutil.serialization.XML

internal class AtomParser(
    private val xml: XML,
    private val httpClient: HttpClient
) {

    suspend fun parse(feed: Feed): ParsedFeed {
        return try {
            val rawAtomXml = httpClient.get(feed.link).bodyAsText()
            val atom = xml.decodeFromString(AtomRoot.serializer(), rawAtomXml)

            ParsedFeed.Success(
                id = feed.id.origin,
                title = feed.title,
                link = atom.link?.href,
                description = "",
                posts = atom.entries?.map { item ->
                    val id = item.id
                    val title = item.title.cleanUpText()
                    val link = item.link.href.replaceHttp()
                    val description = (item.summary ?: item.content).orEmpty().cleanUpText()
                    val pubDate = (item.published ?: item.updated)
                        .parseDateTime(DATE_TIME_FORMAT_RFC_3339)
                        ?: Clock.System.now()
                    val imageUrl = item.imageUrl ?: item.content.orEmpty()
                        .getFirstImageUrlFromHtml()

                    ParsedPost(
                        id = id,
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
            Napier.e(e) { "haha" }
            return ParsedFeed.Error(feed, e)
        }
    }
}