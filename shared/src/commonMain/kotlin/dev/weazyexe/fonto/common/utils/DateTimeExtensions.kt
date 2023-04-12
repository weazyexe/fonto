@file:JvmName("CommonDateTimeExtensions")
package dev.weazyexe.fonto.common.utils

import kotlinx.datetime.LocalDateTime

const val DEFAULT_DATE_TIME_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz"

expect fun String.parseDateTime(format: String = DEFAULT_DATE_TIME_FORMAT): LocalDateTime?