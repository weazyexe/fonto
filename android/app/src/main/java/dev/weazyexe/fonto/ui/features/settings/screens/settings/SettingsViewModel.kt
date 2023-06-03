package dev.weazyexe.fonto.ui.features.settings.screens.settings

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.app.App
import dev.weazyexe.fonto.common.data.bus.AppEvent
import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.data.usecase.backup.GetExportDataUseCase
import dev.weazyexe.fonto.common.feature.backup.AndroidFileSaver
import dev.weazyexe.fonto.common.feature.settings.SettingsStorage
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.components.preferences.model.Group
import dev.weazyexe.fonto.core.ui.components.preferences.model.Preference
import dev.weazyexe.fonto.core.ui.components.preferences.model.Value
import dev.weazyexe.fonto.core.ui.components.preferences.model.findPreference
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.core.ui.utils.DrawableResources
import dev.weazyexe.fonto.core.ui.utils.StringResources
import dev.weazyexe.fonto.util.AppHelper
import dev.weazyexe.fonto.util.stringRes
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsStorage: SettingsStorage,
    private val eventBus: EventBus,
    private val context: App,
    private val getExportData: GetExportDataUseCase
) : CoreViewModel<SettingsState, SettingsEffect>() {

    override val initialState: SettingsState = SettingsState()

    init {
        loadSettings()
    }

    fun loadSettings() = viewModelScope.launch {
        val openPostPreference = settingsStorage.getOpenPostPreference()
        val themePreference = settingsStorage.getTheme()
        val isDynamicColorsEnabled = settingsStorage.isDynamicColorsEnabled()
        val accentColor = settingsStorage.getAccentColor().asColorValue(context)

        val updatedPreferences = buildPreferences(context)
            .map { group ->
                Group(
                    title = group.title,
                    preferences = group.preferences.map { preference ->
                        when (preference.id) {
                            Preference.Identifier.OPEN_POST -> {
                                Preference.Switch(
                                    id = preference.id,
                                    title = preference.title,
                                    subtitle = preference.title,
                                    icon = preference.icon,
                                    value = openPostPreference == OpenPostPreference.INTERNAL
                                )
                            }

                            Preference.Identifier.THEME -> {
                                Preference.CustomValue(
                                    id = preference.id,
                                    title = preference.title,
                                    subtitle = preference.subtitle,
                                    icon = preference.icon,
                                    value = Value(
                                        themePreference,
                                        context.getString(themePreference.stringRes)
                                    ),
                                    possibleValues = Theme.values()
                                        .map { Value(it, context.getString(it.stringRes)) }
                                )
                            }

                            Preference.Identifier.DYNAMIC_COLORS -> {
                                Preference.Switch(
                                    id = preference.id,
                                    title = preference.title,
                                    subtitle = preference.subtitle,
                                    icon = preference.icon,
                                    value = isDynamicColorsEnabled
                                )
                            }

                            Preference.Identifier.COLOR_SCHEME -> {
                                Preference.CustomValue(
                                    id = preference.id,
                                    title = preference.title,
                                    subtitle = preference.subtitle,
                                    icon = preference.icon,
                                    value = Value(accentColor.data, accentColor.title),
                                    possibleValues = buildColors(context)
                                )
                            }

                            Preference.Identifier.MANAGE_FEED,
                            Preference.Identifier.MANAGE_CATEGORIES,
                            Preference.Identifier.EXPORT_FONTO,
                            Preference.Identifier.DEBUG_MENU,
                            -> preference
                        }
                    }
                )
            }

        setState {
            copy(
                preferences = updatedPreferences,
                hiddenPreferences = buildHiddenPreferences(updatedPreferences)
            )
        }
    }

    fun onTextPreferenceClick(preference: Preference.Text) {
        when (preference.id) {
            Preference.Identifier.MANAGE_FEED -> SettingsEffect.OpenManageFeedScreen.emit()
            Preference.Identifier.MANAGE_CATEGORIES -> SettingsEffect.OpenCategoriesScreen.emit()
            Preference.Identifier.EXPORT_FONTO -> SettingsEffect.ExportFonto.emit()
            Preference.Identifier.DEBUG_MENU -> SettingsEffect.OpenDebugScreen.emit()
            else -> {
                // Do nothing
            }
        }
    }

    fun onSwitchPreferenceClick(preference: Preference.Switch, value: Boolean) =
        viewModelScope.launch {
            when (preference.id) {
                Preference.Identifier.OPEN_POST -> onOpenPostChanged(preference, value)
                Preference.Identifier.DYNAMIC_COLORS -> onDynamicColorsEnabledChanged(
                    preference,
                    value
                )

                else -> {
                    // Do nothing
                }
            }
        }

    @Suppress("UNCHECKED_CAST")
    fun <T> onCustomPreferenceClick(preference: Preference.CustomValue<T>) {
        when (preference.id) {
            Preference.Identifier.THEME -> {
                SettingsEffect.OpenThemePickerDialog(
                    id = preference.id,
                    title = StringResources.settings_display_theme_title,
                    icon = DrawableResources.ic_lightbulb_24,
                    value = preference.value as Value<Theme>,
                    possibleValues = preference.possibleValues as List<Value<Theme>>
                ).emit()
            }

            Preference.Identifier.COLOR_SCHEME -> {
                Napier.d { preference.toString() }
                SettingsEffect.OpenColorPickerDialog(
                    id = preference.id,
                    value = preference.value as Value<Long>,
                    possibleValues = preference.possibleValues as List<Value<Long>>
                ).emit()
            }

            else -> {
                // Do nothing
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun saveTheme(theme: Theme) = viewModelScope.launch {
        settingsStorage.saveTheme(theme)
        eventBus.emit(AppEvent.ThemeChanged(theme))

        val preference = state.preferences
            .findPreference(Preference.Identifier.THEME) as? Preference.CustomValue<Theme>
            ?: return@launch

        update(preference.copy(value = Value(theme, context.getString(theme.stringRes))))
    }

    fun saveColor(color: Long) = viewModelScope.launch {
        settingsStorage.saveAccentColor(color)
        eventBus.emit(AppEvent.AccentColorChanged(color))

        val preference = state.preferences
            .findPreference(Preference.Identifier.COLOR_SCHEME) as? Preference.CustomValue<Long>
            ?: return@launch

        val colorValue = color.asColorValue(context)
        update(preference.copy(value = colorValue))
    }

    fun saveFile(uri: Uri) = viewModelScope.launch {
        val exportData = request { getExportData() }
            .withErrorHandling {
                SettingsEffect.ShowMessage(StringResources.settings_export_fonto_data_preparation_failed).emit()
            }?.data ?: return@launch

        request { AndroidFileSaver(context, uri).save(exportData.toByteArray()) }
            .withErrorHandling {
                SettingsEffect.ShowMessage(StringResources.settings_export_fonto_file_saving_failed).emit()
            } ?: return@launch

        SettingsEffect.ShowMessage(StringResources.settings_export_fonto_successful).emit()
    }

    private suspend fun onOpenPostChanged(preference: Preference.Switch, value: Boolean) {
        val newValue = if (value) {
            OpenPostPreference.INTERNAL
        } else {
            OpenPostPreference.DEFAULT_BROWSER
        }
        settingsStorage.saveOpenPostPreference(newValue)
        update(preference.copy(value = value))
    }

    private suspend fun onDynamicColorsEnabledChanged(
        preference: Preference.Switch,
        value: Boolean
    ) {
        settingsStorage.saveDynamicColorsEnabled(value)
        eventBus.emit(AppEvent.DynamicColorsChanged(value))
        update(preference.copy(value = value))
    }

    private fun update(preference: Preference) {
        val updatedPreferences = state.preferences
            .map { group ->
                Group(
                    title = group.title,
                    preferences = group.preferences.map {
                        if (it.id == preference.id) {
                            preference
                        } else {
                            it
                        }
                    }
                )
            }

        setState {
            copy(
                preferences = updatedPreferences,
                hiddenPreferences = buildHiddenPreferences(updatedPreferences)
            )
        }
    }

    private fun buildHiddenPreferences(preferences: List<Group>): List<Preference.Identifier> {
        val isDynamicColorEnabled = (preferences
            .findPreference(Preference.Identifier.DYNAMIC_COLORS) as Preference.Switch)
            .value

        val isAndroid12OrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

        val isReleaseBuild = AppHelper.isReleaseBuild()

        return buildList {
            if (!isAndroid12OrAbove) {
                add(Preference.Identifier.DYNAMIC_COLORS)
            }
            if (isDynamicColorEnabled && isAndroid12OrAbove) {
                add(Preference.Identifier.COLOR_SCHEME)
            }
            if (isReleaseBuild) {
                add(Preference.Identifier.DEBUG_MENU)
            }
        }
    }
}

private fun buildPreferences(context: Context): List<Group> = listOf(
    Group(
        title = StringResources.settings_feed_group,
        preferences = listOf(
            Preference.Text(
                id = Preference.Identifier.MANAGE_FEED,
                title = StringResources.settings_feed_manage_title,
                subtitle = StringResources.settings_feed_manage_description,
                icon = DrawableResources.ic_feed_24
            ),
            Preference.Text(
                id = Preference.Identifier.MANAGE_CATEGORIES,
                title = StringResources.settings_category_manage_title,
                subtitle = StringResources.settings_category_manage_description,
                icon = DrawableResources.ic_category_24
            ),
            Preference.Switch(
                id = Preference.Identifier.OPEN_POST,
                title = StringResources.settings_feed_open_post_title,
                subtitle = StringResources.settings_feed_open_post_description,
                icon = DrawableResources.ic_language_24,
                value = true
            )
        )
    ),
    Group(
        title = StringResources.settings_display_group,
        preferences = listOf(
            Preference.CustomValue(
                id = Preference.Identifier.THEME,
                title = StringResources.settings_display_theme_title,
                subtitle = StringResources.settings_display_theme_description,
                icon = DrawableResources.ic_lightbulb_24,
                value = Value(Theme.SYSTEM, context.getString(Theme.SYSTEM.stringRes)),
                possibleValues = Theme.values().map { Value(it, context.getString(it.stringRes)) }
            ),
            Preference.Switch(
                id = Preference.Identifier.DYNAMIC_COLORS,
                title = StringResources.settings_display_dynamic_colors_title,
                subtitle = StringResources.settings_display_dynamic_colors_description,
                icon = DrawableResources.ic_palette_24,
                value = true
            ),
            Preference.CustomValue(
                id = Preference.Identifier.COLOR_SCHEME,
                title = StringResources.settings_display_color_scheme_title,
                subtitle = StringResources.settings_display_color_scheme_description,
                icon = DrawableResources.ic_format_paint_24,
                value = buildColors(context).first(),
                possibleValues = buildColors(context)
            ),
        )
    ),
    Group(
        title = StringResources.settings_backup_group,
        preferences = listOf(
            Preference.Text(
                id = Preference.Identifier.EXPORT_FONTO,
                title = StringResources.settings_export_fonto_title,
                subtitle = StringResources.settings_export_fonto_description,
                icon = DrawableResources.ic_upload_24
            )
        )
    ),
    Group(
        title = StringResources.settings_debug_group,
        preferences = listOf(
            Preference.Text(
                id = Preference.Identifier.DEBUG_MENU,
                title = StringResources.settings_debug_menu_title,
                subtitle = StringResources.settings_debug_menu_description,
                icon = DrawableResources.ic_bug_24
            )
        )
    )
)

private fun buildColors(context: Context): List<Value<Long>> = listOf(
    Value(
        data = 0xFF6383F8,
        title = context.getString(StringResources.settings_display_color_scheme_value_blue)
    ),
    Value(
        data = 0xFF88CF9B,
        title = context.getString(StringResources.settings_display_color_scheme_value_green)
    ),
    Value(
        data = 0xFFDFA576,
        title = context.getString(StringResources.settings_display_color_scheme_value_orange)
    ),
    Value(
        data = 0xFFCE6E6E,
        title = context.getString(StringResources.settings_display_color_scheme_value_red)
    ),
    Value(
        data = 0xFFFF96EA,
        title = context.getString(StringResources.settings_display_color_scheme_value_pink)
    ),
)

private fun Long?.asColorValue(context: Context): Value<Long> {
    val allColors = buildColors(context)
    return allColors.firstOrNull { it.data == this } ?: allColors.first()
}