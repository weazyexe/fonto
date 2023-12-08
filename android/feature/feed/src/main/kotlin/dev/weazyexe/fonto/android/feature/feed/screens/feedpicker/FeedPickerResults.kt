package dev.weazyexe.fonto.android.feature.feed.screens.feedpicker

import android.os.Parcelable
import com.ramcosta.composedestinations.result.ResultRecipient
import dev.weazyexe.fonto.android.feature.feed.screens.destinations.FeedPickerDialogDestination
import dev.weazyexe.fonto.common.feature.posts.ByFeed
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class FeedPickerResults(
    val values: @RawValue List<ByFeed.Data>,
    val possibleValues: @RawValue List<ByFeed.Data>
) : Parcelable

typealias FeedPickerResult = ResultRecipient<FeedPickerDialogDestination, FeedPickerResults?>
