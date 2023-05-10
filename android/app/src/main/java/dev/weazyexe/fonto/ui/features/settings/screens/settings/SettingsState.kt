package dev.weazyexe.fonto.ui.features.settings.screens.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.components.preferences.model.Group
import dev.weazyexe.fonto.core.ui.components.preferences.model.Preference
import dev.weazyexe.fonto.core.ui.components.preferences.model.Value
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.State
import dev.weazyexe.fonto.core.ui.theme.COLORS
import dev.weazyexe.fonto.core.ui.theme.DEFAULT_COLOR
import dev.weazyexe.fonto.util.stringRes

data class SettingsState(
    val preferences: List<Group> = PREFERENCES,
    val openPostPreference: OpenPostPreference = OpenPostPreference.INTERNAL,
    val hiddenPreferences: List<Preference.Identifier> = emptyList()
) : State

sealed interface SettingsEffect : Effect {

    object OpenManageFeedScreen : SettingsEffect

    object OpenDebugScreen : SettingsEffect

    data class OpenThemePickerDialog(
        val id: Preference.Identifier,
        @StringRes val title: Int,
        @DrawableRes val icon: Int,
        val value: Value<Theme>,
        val possibleValues: List<Value<Theme>>
    ) : SettingsEffect

    data class OpenColorPickerDialog(
        val id: Preference.Identifier,
        val value: Value<Long>,
        val possibleValues: List<Value<Long>>
    ) : SettingsEffect
}

private val PREFERENCES = listOf(
    Group(
        title = R.string.settings_feed_group,
        preferences = listOf(
            Preference.Text(
                id = Preference.Identifier.MANAGE_FEED,
                title = R.string.settings_feed_manage_title,
                subtitle = R.string.settings_feed_manage_description,
                icon = R.drawable.ic_feed_24
            ),
            Preference.Switch(
                id = Preference.Identifier.OPEN_POST,
                title = R.string.settings_feed_open_post_title,
                subtitle = R.string.settings_feed_open_post_description,
                icon = R.drawable.ic_language_24,
                value = true
            )
        )
    ),
    Group(
        title = R.string.settings_display_group,
        preferences = listOf(
            Preference.CustomValue(
                id = Preference.Identifier.THEME,
                title = R.string.settings_display_theme_title,
                subtitle = R.string.settings_display_theme_description,
                icon = R.drawable.ic_lightbulb_24,
                value = Value(Theme.SYSTEM, Theme.SYSTEM.stringRes),
                possibleValues = Theme.values().map { Value(it, it.stringRes) }
            ),
            Preference.Switch(
                id = Preference.Identifier.DYNAMIC_COLORS,
                title = R.string.settings_display_dynamic_colors_title,
                subtitle = R.string.settings_display_dynamic_colors_description,
                icon = R.drawable.ic_palette_24,
                value = true
            ),
            Preference.CustomValue(
                id = Preference.Identifier.COLOR_SCHEME,
                title = R.string.settings_display_color_scheme_title,
                subtitle = R.string.settings_display_color_scheme_description,
                icon = R.drawable.ic_format_paint_24,
                value = DEFAULT_COLOR,
                possibleValues = COLORS
            ),
        )
    ),
    Group(
        title = R.string.settings_debug_group,
        preferences = listOf(
            Preference.Text(
                id = Preference.Identifier.DEBUG_MENU,
                title = R.string.settings_debug_menu_title,
                subtitle = R.string.settings_debug_menu_description,
                icon = R.drawable.ic_bug_24
            )
        )
    )
)