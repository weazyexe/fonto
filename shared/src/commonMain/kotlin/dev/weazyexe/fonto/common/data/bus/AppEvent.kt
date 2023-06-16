package dev.weazyexe.fonto.common.data.bus

import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.Theme

sealed interface AppEvent {

    object RefreshFeed : AppEvent

    data class ThemeChanged(val theme: Theme) : AppEvent

    data class DynamicColorsChanged(val isEnabled: Boolean) : AppEvent

    data class ColorSchemeChanged(val color: ColorScheme) : AppEvent
}