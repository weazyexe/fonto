package dev.weazyexe.fonto.common.feature.parser.atom

import com.ouattararomuald.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ouattararomuald.syndication.Syndication
import com.ouattararomuald.syndication.atom.AtomFeed
import dev.weazyexe.fonto.common.feature.parser.ParsedFeed
import dev.weazyexe.fonto.common.feature.parser.ParsedPost
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.utils.DATE_TIME_FORMAT_RFC_3339
import dev.weazyexe.fonto.common.utils.cleanUpText
import dev.weazyexe.fonto.common.utils.getFirstImageUrlFromHtml
import dev.weazyexe.fonto.common.utils.parseDateTime
import dev.weazyexe.fonto.common.utils.replaceHttp
import kotlinx.coroutines.Deferred
import kotlinx.datetime.Clock

internal actual class AtomParser {

    actual suspend fun parse(feed: Feed): ParsedFeed {
        return try {
            val reader = Syndication(
                url = feed.link,
                callFactory = CoroutineCallAdapterFactory()
            ).create(Reader::class.java)
            val parsedFeed = reader.read().await()
            ParsedFeed.Success(
                id = feed.id.origin,
                title = parsedFeed.title.value?.cleanUpText().orEmpty(),
                link = parsedFeed.baseUri.orEmpty(),
                description = parsedFeed.subtitle?.value.orEmpty(),
                icon = feed.icon,
                posts = parsedFeed.items?.map {
                    ParsedPost(
                        title = it.title,
                        link = it.links?.firstOrNull()?.href.orEmpty().replaceHttp(),
                        content = it.content?.value,
                        description = (it.summary?.value
                            ?: it.content?.value.orEmpty())
                            .cleanUpText(),
                        pubDate = it.lastUpdatedTime
                            .parseDateTime(format = DATE_TIME_FORMAT_RFC_3339)
                            ?: Clock.System.now(),
                        imageUrl = it.content?.value?.getFirstImageUrlFromHtml(),
                        feed = feed
                    )
                }.orEmpty()
            )
        } catch (t: Throwable) {
            ParsedFeed.Error(
                feed = feed,
                throwable = t
            )
        }
    }

    private interface Reader {

        fun read(): Deferred<AtomFeed>
    }
}