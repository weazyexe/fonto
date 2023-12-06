package dev.weazyexe.fonto.feature.feed.utils

import androidx.annotation.StringRes
import androidx.browser.customtabs.CustomTabsIntent
import dev.weazyexe.fonto.common.model.preference.Theme

val Theme.customTabsColorScheme : Int
    @StringRes
    get() = when (this) {
        Theme.LIGHT -> CustomTabsIntent.COLOR_SCHEME_LIGHT
        Theme.DARK -> CustomTabsIntent.COLOR_SCHEME_DARK
        Theme.SYSTEM -> CustomTabsIntent.COLOR_SCHEME_SYSTEM
    }
