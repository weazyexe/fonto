@file:JvmName("CommonDateTimeExtensions")

package dev.weazyexe.fonto.common.utils

import kotlinx.datetime.Instant

const val DEFAULT_DATE_TIME_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz"
const val DATE_TIME_FORMAT_RFC_3339 = "yyyy-MM-dd'T'HH:mm:ssX"
const val DATE_TIME_FORMAT_WITH_MILLIS = "yyyy-MM-dd'T'HH:mm:ss.SSS"

const val HUMAN_READABLE_DATE_TIME_YEAR_FORMAT = "dd MMM yyyy, HH:mm"
const val HUMAN_READABLE_DATE_TIME_FORMAT = "dd MMM, HH:mm"
const val HUMAN_READABLE_DATE_FORMAT = "dd MMM, yyyy"
const val HUMAN_READABLE_TIME_FORMAT = "HH:mm"

fun String.bruteForceParseDateTime(): Instant? {
    listOf(
        DATE_TIME_FORMAT_RFC_3339,
        DATE_TIME_FORMAT_WITH_MILLIS,
        DEFAULT_DATE_TIME_FORMAT
    ).forEach { format ->
        val date = catch { this.parseDateTime(format) }
        if (date != null) {
            return date
        }
    }

    return null
}

expect fun String.parseDateTime(format: String = DEFAULT_DATE_TIME_FORMAT): Instant?

expect fun Instant.format(
    pattern: String = DEFAULT_DATE_TIME_FORMAT
): String

private fun <T> catch(block: () -> T): T? =
    try {
        block()
    } catch (t: Throwable) {
        null
    }