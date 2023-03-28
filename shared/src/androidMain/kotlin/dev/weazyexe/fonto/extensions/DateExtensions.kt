package dev.weazyexe.fonto.extensions

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

actual fun LocalDateTime.format(pattern: String): String =
    DateTimeFormatter.ofPattern(pattern).format(this.toJavaLocalDateTime())