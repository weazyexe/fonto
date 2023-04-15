@file:JvmName("CommonStringExtensions")
package dev.weazyexe.fonto.common.utils

expect fun String.getHostnameWithScheme(): String

expect fun String.isUrlValid(): Boolean

fun String.cleanUpText(): String {
    val imgTagRegex = Regex("<img .*?>")
    val textWithoutImages = imgTagRegex.replace(this, "")
    return textWithoutImages.fixHtmlEntities()
}

expect fun String.fixHtmlEntities(): String