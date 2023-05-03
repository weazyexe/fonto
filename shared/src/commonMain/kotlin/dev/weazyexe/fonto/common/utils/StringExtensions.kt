@file:JvmName("CommonStringExtensions")
package dev.weazyexe.fonto.common.utils

expect fun String.getHostnameWithScheme(): String

expect fun String.isUrlValid(): Boolean

fun String.cleanUpText(): String {
    val imgTagRegex = Regex("<img .*?>")
    val textWithoutImages = imgTagRegex.replace(this, "")
    return textWithoutImages.fixHtmlEntities().trim()
}

fun String.replaceHttp(): String {
    return replace("http://", "https://")
}

fun String.getFirstImageUrlFromHtml(): String? {
    val imgRegex = Regex("\\<img.+src\\=(?:\\\"|\\')(.+?)(?:\\\"|\\')(?:.+?)\\>")
    val decodedHtml = this.decodeHtml()
    val result = imgRegex.find(decodedHtml)
    return result?.groupValues?.firstOrNull { it.startsWith("https://") }
}

expect fun String.fixHtmlEntities(): String

fun String.decodeHtml(): String {
    return replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&amp;", "&")
        .replace("&#39;", "\\")
        .replace("&quot;", "\"")
}