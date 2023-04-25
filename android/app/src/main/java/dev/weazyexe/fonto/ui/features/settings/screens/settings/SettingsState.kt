package dev.weazyexe.fonto.ui.features.settings.screens.settings

import android.os.Build
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
import dev.weazyexe.fonto.util.stringRes

data class SettingsState(
    val preferences: List<Group> = PREFERENCES,
    val openPostPreference: OpenPostPreference = OpenPostPreference.INTERNAL
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
        preferences = listOfNotNull(
            Preference.CustomValue(
                id = Preference.Identifier.THEME,
                title = R.string.settings_display_theme_title,
                subtitle = R.string.settings_display_theme_description,
                icon = R.drawable.ic_lightbulb_24,
                value = Value(Theme.SYSTEM, Theme.SYSTEM.stringRes),
                possibleValues = Theme.values().map { Value(it, it.stringRes) }
            ),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Preference.Switch(
                    id = Preference.Identifier.DYNAMIC_COLORS,
                    title = R.string.settings_display_dynamic_colors_title,
                    subtitle = R.string.settings_display_dynamic_colors_description,
                    icon = R.drawable.ic_palette_24,
                    value = true
                )
            } else null
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