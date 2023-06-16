package dev.weazyexe.fonto.ui.features.settings.screens.settings.mapper

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.Group
import dev.weazyexe.fonto.common.model.preference.Preference
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.common.model.preference.ValuePreference
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.features.settings.SettingsDomainState
import dev.weazyexe.fonto.ui.features.settings.screens.settings.SettingsViewState
import dev.weazyexe.fonto.ui.features.settings.screens.settings.viewstate.GroupViewState
import dev.weazyexe.fonto.ui.features.settings.screens.settings.viewstate.GroupsViewState
import dev.weazyexe.fonto.ui.features.settings.screens.settings.viewstate.PreferenceViewState

fun PreferenceViewState.asDomainState(): Preference =
    when (this) {
        is PreferenceViewState.Text -> {
            Preference.Text(key = key)
        }

        is PreferenceViewState.Switch -> {
            Preference.Switch(
                key = key,
                value = value
            )
        }

        is PreferenceViewState.Value<*> -> {
            Preference.Value(
                key = key,
                value = value,
                possibleValues = possibleValues,
            )
        }
    }

fun SettingsDomainState.asViewState(context: Context): SettingsViewState =
    SettingsViewState(
        preferences = GroupsViewState(preferences.map { it.asViewState(context) }),
        isLoading = isLoading
    )

private fun Group.asViewState(context: Context): GroupViewState =
    GroupViewState(
        key = key,
        title = key.title(context),
        preferences = preferences.map { it.asViewState(context) }
    )

private fun Preference.asViewState(context: Context): PreferenceViewState {
    val visuals = key.visuals(context)
    return when (this) {
        is Preference.Text -> {
            PreferenceViewState.Text(
                key = key,
                title = visuals.title,
                description = visuals.description,
                icon = visuals.icon,
                isVisible = isVisible
            )
        }

        is Preference.Switch -> {
            PreferenceViewState.Switch(
                key = key,
                title = visuals.title,
                description = visuals.description,
                icon = visuals.icon,
                value = value,
                isVisible = isVisible
            )
        }

        is Preference.Value<*> -> {
            PreferenceViewState.Value(
                key = key,
                title = visuals.title,
                description = visuals.description,
                icon = visuals.icon,
                value = value,
                possibleValues = possibleValues,
                displayValue = value.asDisplayValue(context),
                isVisible = isVisible
            )
        }
    }
}

private fun Group.Key.title(context: Context): String =
    context.getString(
        when (this) {
            Group.Key.FEED -> StringResources.settings_feed_group
            Group.Key.APPEARANCE -> StringResources.settings_appearance_group
            Group.Key.BACKUP -> StringResources.settings_backup_group
            Group.Key.DEBUG -> StringResources.settings_debug_group
        }
    )

private fun Preference.Key.visuals(context: Context): PreferenceVisuals =
    when (this) {
        Preference.Key.OPEN_POST -> PreferenceVisuals(
            title = context.getString(StringResources.settings_feed_open_post_title),
            description = context.getString(StringResources.settings_feed_open_post_description),
            icon = DrawableResources.ic_language_24
        )

        Preference.Key.THEME -> PreferenceVisuals(
            title = context.getString(StringResources.settings_appearance_theme_title),
            description = context.getString(StringResources.settings_appearance_theme_description),
            icon = DrawableResources.ic_lightbulb_24
        )

        Preference.Key.DYNAMIC_COLORS -> PreferenceVisuals(
            title = context.getString(StringResources.settings_appearance_dynamic_colors_title),
            description = context.getString(StringResources.settings_appearance_dynamic_colors_description),
            icon = DrawableResources.ic_palette_24
        )

        Preference.Key.COLOR_SCHEME -> PreferenceVisuals(
            title = context.getString(StringResources.settings_appearance_color_scheme_title),
            description = context.getString(StringResources.settings_appearance_color_scheme_description),
            icon = DrawableResources.ic_format_paint_24
        )

        Preference.Key.MANAGE_FEED -> PreferenceVisuals(
            title = context.getString(StringResources.settings_feed_manage_title),
            description = context.getString(StringResources.settings_feed_manage_description),
            icon = DrawableResources.ic_feed_24
        )

        Preference.Key.MANAGE_CATEGORIES -> PreferenceVisuals(
            title = context.getString(StringResources.settings_category_manage_title),
            description = context.getString(StringResources.settings_category_manage_description),
            icon = DrawableResources.ic_category_24
        )

        Preference.Key.EXPORT_FONTO -> PreferenceVisuals(
            title = context.getString(StringResources.settings_export_fonto_title),
            description = context.getString(StringResources.settings_export_fonto_description),
            icon = DrawableResources.ic_upload_24
        )

        Preference.Key.IMPORT_FONTO -> PreferenceVisuals(
            title = context.getString(StringResources.settings_import_fonto_title),
            description = context.getString(StringResources.settings_import_fonto_description),
            icon = DrawableResources.ic_download_24
        )

        Preference.Key.DEBUG_MENU -> PreferenceVisuals(
            title = context.getString(StringResources.settings_debug_menu_title),
            description = context.getString(StringResources.settings_debug_menu_description),
            icon = DrawableResources.ic_bug_24
        )
    }

private fun ValuePreference.asDisplayValue(context: Context): String =
    context.getString(
        when (this) {
            is Theme -> stringRes
            is ColorScheme -> stringRes

            else -> throw IllegalArgumentException("Unknown value preference")
        }
    )

@get:StringRes
val Theme.stringRes: Int
    get() = when (this) {
        Theme.LIGHT -> StringResources.settings_appearance_theme_value_light
        Theme.DARK -> StringResources.settings_appearance_theme_value_dark
        Theme.SYSTEM -> StringResources.settings_appearance_theme_value_system
    }

@get:StringRes
val ColorScheme.stringRes: Int
    get() = when (this) {
        ColorScheme.BLUE -> StringResources.settings_appearance_color_scheme_value_blue
        ColorScheme.GREEN -> StringResources.settings_appearance_color_scheme_value_green
        ColorScheme.ORANGE -> StringResources.settings_appearance_color_scheme_value_orange
        ColorScheme.RED -> StringResources.settings_appearance_color_scheme_value_red
        ColorScheme.PINK -> StringResources.settings_appearance_color_scheme_value_pink
    }

private data class PreferenceVisuals(
    val title: String,
    val description: String,
    @DrawableRes val icon: Int
)