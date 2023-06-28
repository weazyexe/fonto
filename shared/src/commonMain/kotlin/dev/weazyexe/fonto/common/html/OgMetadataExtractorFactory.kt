package dev.weazyexe.fonto.common.html

internal expect class OgMetadataExtractorFactory {

    fun create() : OgMetadataExtractor
}

internal fun createOgImageExtractor(factory: OgMetadataExtractorFactory): OgMetadataExtractor {
    return factory.create()
}