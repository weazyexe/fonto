package dev.weazyexe.fonto.common.html

import org.jsoup.Jsoup

internal class AndroidOgImageExtractor : OgImageExtractor {

    override fun extract(html: String): String? {
        val document = Jsoup.parse(html)
        val ogImageTag = document.head()
            .select("meta[property=og:image]")
            .firstOrNull()
            ?: return null

        return ogImageTag.attr("content").ifBlank { null }
    }
}