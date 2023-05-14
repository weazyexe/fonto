package dev.weazyexe.fonto.ui.features.home.dependencies

import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.ui.features.destinations.FeedPickerDialogDestination
import dev.weazyexe.fonto.ui.features.feed.screens.feedpicker.FeedPickerResult

interface FeedPickerResults {

    fun invoke(): ResultRecipient<FeedPickerDialogDestination, FeedPickerResult?>
}