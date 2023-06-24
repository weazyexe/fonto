package dev.weazyexe.fonto.common.html

import org.jsoup.Jsoup

internal class AndroidOgMetadataExtractor : OgMetadataExtractor {

    override fun extract(html: String): OgMetadata {
        val document = Jsoup.parse(html)
        val ogImageTag = document.head()
            .select("meta[property=og:image]")
            .firstOrNull()

        val ogDescriptionTag = document.head()
            .select("meta[property=og:description]")
            .firstOrNull()

        val imageUrl = ogImageTag?.attr("content")?.ifBlank { null }
        val description = ogDescriptionTag?.attr("content")?.ifBlank { null }

        return OgMetadata(
            imageUrl = imageUrl,
            description = description
        )
    }
}