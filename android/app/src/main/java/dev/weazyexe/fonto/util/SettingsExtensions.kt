package dev.weazyexe.fonto.util

import androidx.annotation.StringRes
import androidx.browser.customtabs.CustomTabsIntent
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.utils.StringResources

val Theme.stringRes : Int
    @StringRes
    get() = when (this) {
        Theme.LIGHT -> StringResources.settings_appearance_theme_value_light
        Theme.DARK -> StringResources.settings_appearance_theme_value_dark
        Theme.SYSTEM -> StringResources.settings_appearance_theme_value_system
    }

val Theme.customTabsColorScheme : Int
    @StringRes
    get() = when (this) {
        Theme.LIGHT -> CustomTabsIntent.COLOR_SCHEME_LIGHT
        Theme.DARK -> CustomTabsIntent.COLOR_SCHEME_DARK
        Theme.SYSTEM -> CustomTabsIntent.COLOR_SCHEME_SYSTEM
    }