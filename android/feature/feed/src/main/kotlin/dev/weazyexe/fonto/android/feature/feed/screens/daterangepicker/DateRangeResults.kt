package dev.weazyexe.fonto.android.feature.feed.screens.daterangepicker

import android.os.Parcelable
import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.android.feature.feed.screens.destinations.DateRangePickerDialogDestination
import kotlinx.datetime.Instant
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class DateRangeResults(
    val from: @RawValue Instant,
    val to: @RawValue Instant
) : Parcelable

typealias DateRangePickerResult = ResultRecipient<DateRangePickerDialogDestination, DateRangeResults?>
