@file:JvmName("CommonStringExtensions")
package dev.weazyexe.fonto.common.utils

internal expect fun String.getHostnameWithScheme(): String

internal expect fun String.isUrlValid(): Boolean

internal fun String.cleanUpText(): String {
    val imgTagRegex = Regex("<img .*?>")
    val textWithoutImages = imgTagRegex.replace(this, "")
    return textWithoutImages.fixHtmlEntities().trim()
}

internal fun String.replaceHttp(): String {
    return replace("http://", "https://")
}

internal fun String.getFirstImageUrlFromHtml(): String? {
    val imgRegex = Regex("\\<img.+src\\=(?:\\\"|\\')(.+?)(?:\\\"|\\')(?:.+?)\\>")
    val decodedHtml = this.decodeHtml()
    val result = imgRegex.find(decodedHtml)
    return result?.groupValues?.firstOrNull { it.startsWith("https://") }
}

internal expect fun String.fixHtmlEntities(): String

internal fun String.decodeHtml(): String {
    return replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&amp;", "&")
        .replace("&#39;", "\\")
        .replace("&quot;", "\"")
}