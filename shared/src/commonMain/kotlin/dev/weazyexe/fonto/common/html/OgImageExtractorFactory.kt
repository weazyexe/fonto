package dev.weazyexe.fonto.common.html

internal expect class OgImageExtractorFactory {

    fun create() : OgImageExtractor
}

internal fun createOgImageExtractor(factory: OgImageExtractorFactory): OgImageExtractor {
    return factory.create()
}