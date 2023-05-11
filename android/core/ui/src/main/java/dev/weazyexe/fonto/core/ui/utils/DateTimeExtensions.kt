package dev.weazyexe.fonto.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalContext
import dev.weazyexe.fonto.common.utils.HUMAN_READABLE_DATE_TIME_FORMAT
import dev.weazyexe.fonto.common.utils.HUMAN_READABLE_DATE_TIME_YEAR_FORMAT
import dev.weazyexe.fonto.common.utils.HUMAN_READABLE_TIME_FORMAT
import dev.weazyexe.fonto.common.utils.format
import dev.weazyexe.fonto.core.ui.R
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Stable
@Composable
fun Instant.formatHumanFriendly(): String {
    val context = LocalContext.current
    val now = Clock.System.now()

    val nowDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
    val dateTime = this.toLocalDateTime(TimeZone.currentSystemDefault())

    val subtract = now.epochSeconds - this.epochSeconds

    val isSecondsRange = subtract < 60
    val isMinuteRange = subtract < 3600
    val isHoursRange = subtract < 86_400
    val isYesterdayRange = subtract < 86_400 * 2 && nowDateTime.dayOfMonth - dateTime.dayOfMonth == 1
    val isTheSameYear = nowDateTime.year == dateTime.year

    return when {
        isSecondsRange -> context.getString(R.string.datetime_less_than_minute)
        isMinuteRange -> {
            val minutesAgo = (subtract / 60).toInt()
            context.resources.getQuantityString(
                R.plurals.datetime_minutes_ago,
                minutesAgo,
                minutesAgo
            )
        }

        isHoursRange -> {
            val hoursAgo = (subtract / 3600).toInt()
            context.resources.getQuantityString(
                R.plurals.datetime_hours_ago,
                hoursAgo,
                hoursAgo
            )
        }

        isYesterdayRange ->
            context.getString(
                R.string.datetime_yesterday,
                this.format(pattern = HUMAN_READABLE_TIME_FORMAT)
            )

        isTheSameYear -> this.format(pattern = HUMAN_READABLE_DATE_TIME_FORMAT)

        else -> this.format(pattern = HUMAN_READABLE_DATE_TIME_YEAR_FORMAT)
    }
}