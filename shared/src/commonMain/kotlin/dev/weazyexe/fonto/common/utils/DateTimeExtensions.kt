@file:JvmName("CommonDateTimeExtensions")
package dev.weazyexe.fonto.common.utils

import kotlinx.datetime.Instant

const val DEFAULT_DATE_TIME_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz"
const val DATE_TIME_FORMAT_RFC_3339 = "yyyy-MM-ddTHH:mm:ssZ"
const val HUMAN_READABLE_DATE_TIME_FORMAT = "HH:mm, dd MMM yyyy"

expect fun String.parseDateTime(format: String = DEFAULT_DATE_TIME_FORMAT): Instant?

expect fun Instant.format(
    pattern: String = DEFAULT_DATE_TIME_FORMAT
): String