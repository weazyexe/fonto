package dev.weazyexe.fonto.util

import androidx.annotation.StringRes
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.R

val Theme.stringRes : Int
    @StringRes
    get() = when (this) {
        Theme.LIGHT -> R.string.settings_display_theme_value_light
        Theme.DARK -> R.string.settings_display_theme_value_dark
        Theme.SYSTEM -> R.string.settings_display_theme_value_system
    }