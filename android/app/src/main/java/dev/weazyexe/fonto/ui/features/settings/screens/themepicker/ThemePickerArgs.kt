package dev.weazyexe.fonto.ui.features.settings.screens.themepicker

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.weazyexe.fonto.common.model.preference.Theme
import kotlinx.serialization.Serializable

@Serializable
data class ThemePickerArgs(
    val value: Theme,
    val possibleValues: List<Theme>,
    @DrawableRes val icon: Int,
    @StringRes val title: Int
)
