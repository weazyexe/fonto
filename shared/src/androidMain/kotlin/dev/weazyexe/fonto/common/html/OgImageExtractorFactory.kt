package dev.weazyexe.fonto.common.html

internal actual class OgImageExtractorFactory {

    actual fun create(): OgImageExtractor = AndroidOgImageExtractor()
}