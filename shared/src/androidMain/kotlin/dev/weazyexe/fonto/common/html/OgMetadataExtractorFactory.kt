package dev.weazyexe.fonto.common.html

internal actual class OgMetadataExtractorFactory {

    actual fun create(): OgMetadataExtractor = AndroidOgMetadataExtractor()
}