package dev.weazyexe.fonto.ui.features.feed.screens.feedpicker

import android.os.Parcelable
import dev.weazyexe.fonto.common.feature.posts.ByFeed
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class FeedPickerResult(
    val values: @RawValue List<ByFeed.Data>,
    val possibleValues: @RawValue List<ByFeed.Data>
) : Parcelable