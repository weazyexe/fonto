package dev.weazyexe.fonto.common.parser.atom

import com.ouattararomuald.syndication.Syndication
import dev.weazyexe.fonto.common.model.feed.Feed
import dev.weazyexe.fonto.common.utils.DATE_TIME_FORMAT_RFC_3339
import dev.weazyexe.fonto.common.utils.parseDateTime
import kotlinx.coroutines.Deferred
import kotlinx.datetime.Clock
import com.ouattararomuald.syndication.atom.AtomFeed as LibAtomFeed

actual class AtomParser {

    actual suspend fun parse(feed: Feed): AtomFeed {
        return try {
            val reader = Syndication(url = feed.link).create(Reader::class.java)
            val parsedFeed = reader.read().await()
            AtomFeed.Success(
                id = feed.id.origin,
                title = parsedFeed.title.value.orEmpty(),
                link = parsedFeed.baseUri.orEmpty(),
                description = parsedFeed.subtitle?.value.orEmpty(),
                icon = feed.icon,
                posts = parsedFeed.items?.map {
                    AtomPost(
                        title = it.title,
                        link = it.links?.firstOrNull()?.value.orEmpty(),
                        content = it.content?.value,
                        description = it.summary?.value ?: it.content?.value.orEmpty(),
                        pubDate = it.lastUpdatedTime
                            .parseDateTime(format = DATE_TIME_FORMAT_RFC_3339)
                            ?: Clock.System.now(),
                        imageUrl = null,
                        feed = feed
                    )
                }.orEmpty()
            )
        } catch (t: Throwable) {
            AtomFeed.Error(
                feed = feed,
                throwable = t
            )
        }
    }

    private interface Reader {

        fun read(): Deferred<LibAtomFeed>
    }
}