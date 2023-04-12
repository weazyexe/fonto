package dev.weazyexe.fonto.common.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import java.text.SimpleDateFormat
import java.util.*

actual fun String.parseDateTime(format: String): LocalDateTime? {
    val formatter = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")
    val date = formatter.parse(this)

    val calendar = Calendar.getInstance()
    calendar.time = date ?: return null

    return LocalDateTime(
        year = calendar.get(Calendar.YEAR),
        month = Month(calendar.get(Calendar.MONTH)),
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH),
        hour = calendar.get(Calendar.HOUR),
        minute = calendar.get(Calendar.MINUTE),
        second = calendar.get(Calendar.SECOND)
    )
}