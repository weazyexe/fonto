package dev.weazyexe.fonto.common.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.toKotlinInstant
import java.text.SimpleDateFormat
import java.util.Date

actual fun String.parseDateTime(format: String): Instant? {
    val formatter = SimpleDateFormat(format)
    val date = formatter.parse(this)
    return date.toInstant().toKotlinInstant()
}

actual fun Instant.format(pattern: String): String {
    val date = Date(this.toEpochMilliseconds())
    return SimpleDateFormat(pattern).format(date)
}
