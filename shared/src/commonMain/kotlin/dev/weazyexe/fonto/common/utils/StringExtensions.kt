@file:JvmName("CommonStringExtensions")
package dev.weazyexe.fonto.common.utils

expect fun String.getHostnameWithScheme(): String

expect fun String.isUrlValid(): Boolean

fun String.filterHtmlTags(): String {
    val regex = Regex("<\\/?[^>]+(>|\$)")
    return regex.replace(this, "")
}