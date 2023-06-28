package dev.weazyexe.fonto.common.html

internal interface OgMetadataExtractor {

    fun extract(html: String): OgMetadata
}