@file:JvmName("DateExtensionsCommon")
package dev.weazyexe.fonto.extensions

import kotlinx.datetime.LocalDateTime

const val DATE_FORMAT_DEFAULT = "HH:mm, dd MMM yyyy"

expect fun LocalDateTime.format(
    pattern: String = DATE_FORMAT_DEFAULT
): String