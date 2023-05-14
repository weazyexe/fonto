package dev.weazyexe.fonto.ui.features.feed.screens.feedpicker

import androidx.annotation.StringRes
import kotlinx.serialization.Serializable

@Serializable
data class FeedPickerArgs(
    val values: List<MinimizedFeed>,
    val possibleValues: List<MinimizedFeed>,
    @StringRes val title: Int
)