package dev.weazyexe.fonto.common.model.preference

enum class SyncPostsInterval(val hours: Int) : ValuePreference {
    ONE_HOUR(1),
    TWO_HOURS(2),
    FOUR_HOURS(4),
    EIGHT_HOURS(8),
    TWELVE_HOURS(12),
    SIXTEEN_HOURS(16),
    TWENTY_FOUR_HOURS(24);

    companion object {

        fun byHours(hours: Int): SyncPostsInterval =
            values().first { it.hours == hours }
    }
}