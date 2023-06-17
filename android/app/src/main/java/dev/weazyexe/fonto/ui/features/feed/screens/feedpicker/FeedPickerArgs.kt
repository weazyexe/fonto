package dev.weazyexe.fonto.ui.features.feed.screens.feedpicker

import androidx.annotation.StringRes
import dev.weazyexe.fonto.common.feature.posts.ByFeed
import kotlinx.serialization.Serializable

@Serializable
data class FeedPickerArgs(
    val values: List<ByFeed.Data>,
    val possibleValues: List<ByFeed.Data>,
    @StringRes val title: Int
)