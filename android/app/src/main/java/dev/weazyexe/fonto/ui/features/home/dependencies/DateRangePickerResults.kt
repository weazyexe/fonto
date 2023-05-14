package dev.weazyexe.fonto.ui.features.home.dependencies

import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.ui.features.destinations.DateRangePickerDialogDestination
import dev.weazyexe.fonto.ui.features.feed.screens.daterangepicker.DateRangeResults

interface DateRangePickerResults {

    fun invoke(): ResultRecipient<DateRangePickerDialogDestination, DateRangeResults?>
}