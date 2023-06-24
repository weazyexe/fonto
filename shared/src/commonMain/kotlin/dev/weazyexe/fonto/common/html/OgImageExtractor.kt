package dev.weazyexe.fonto.common.html

internal interface OgImageExtractor {

    fun extract(html: String): String?
}