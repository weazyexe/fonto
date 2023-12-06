package dev.weazyexe.fonto.core.ui.utils

import androidx.annotation.StringRes
import dev.weazyexe.fonto.common.model.preference.Theme

val Theme.stringRes : Int
    @StringRes
    get() = when (this) {
        Theme.LIGHT -> StringResources.settings_appearance_theme_value_light
        Theme.DARK -> StringResources.settings_appearance_theme_value_dark
        Theme.SYSTEM -> StringResources.settings_appearance_theme_value_system
    }
