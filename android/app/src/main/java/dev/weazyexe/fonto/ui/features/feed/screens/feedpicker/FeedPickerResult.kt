package dev.weazyexe.fonto.ui.features.feed.screens.feedpicker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedPickerResult(
    val values: List<MinimizedFeed>,
    val possibleValues: List<MinimizedFeed>
) : Parcelable