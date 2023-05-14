package dev.weazyexe.fonto.ui.features.feed.screens.daterangepicker

import android.os.Parcelable
import kotlinx.datetime.Instant
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class DateRangeResults(
    val from: @RawValue Instant,
    val to: @RawValue Instant
) : Parcelable