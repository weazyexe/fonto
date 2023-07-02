package dev.weazyexe.fonto.ui.features.settings.screens.syncintervalpicker

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.weazyexe.fonto.common.model.preference.SyncPostsInterval
import kotlinx.serialization.Serializable

@Serializable
data class SyncIntervalPickerArgs(
    val value: SyncPostsInterval,
    val possibleValues: List<SyncPostsInterval>,
    @DrawableRes val icon: Int,
    @StringRes val title: Int
)