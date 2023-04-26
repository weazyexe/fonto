package dev.weazyexe.fonto.ui.features.settings.screens.themepicker

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.components.preferences.model.Value
import kotlinx.serialization.Serializable

@Serializable
data class ThemePickerArgs(
    val value: Value<Theme>,
    val possibleValues: List<Value<Theme>>,
    @DrawableRes val icon: Int,
    @StringRes val title: Int
)