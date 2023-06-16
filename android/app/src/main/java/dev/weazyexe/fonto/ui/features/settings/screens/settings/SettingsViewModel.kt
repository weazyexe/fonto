package dev.weazyexe.fonto.ui.features.settings.screens.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.app.App
import dev.weazyexe.fonto.common.feature.backup.AndroidFileReader
import dev.weazyexe.fonto.common.feature.backup.AndroidFileSaver
import dev.weazyexe.fonto.common.model.backup.ExportStrategy
import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.features.settings.SettingsPresentation
import dev.weazyexe.fonto.ui.features.settings.screens.settings.mapper.asDomainState
import dev.weazyexe.fonto.ui.features.settings.screens.settings.mapper.asViewState
import dev.weazyexe.fonto.ui.features.settings.screens.settings.viewstate.PreferenceViewState
import kotlinx.coroutines.flow.map

class SettingsViewModel(
    private val presentation: SettingsPresentation,
    private val context: App
) : ViewModel() {

    val state = presentation.domainState.map { it.asViewState(context) }
    val effects = presentation.effects

    init {
        presentation.onCreate(viewModelScope)
    }

    fun onPreferenceClick(preferenceViewState: PreferenceViewState) {
        val preference = preferenceViewState.asDomainState()
        presentation.onPreferenceClick(preference)
    }

    fun onThemePicked(theme: Theme) {
        presentation.onThemePicked(theme)
    }

    fun onColorSchemePicked(colorScheme: ColorScheme) {
        presentation.onColorSchemePicked(colorScheme)
    }

    fun chooseExportFileDestination(strategy: ExportStrategy) {
        presentation.chooseExportFileDestination(strategy)
    }

    fun export(uri: Uri) {
        val saver = AndroidFileSaver(context, uri)
        presentation.export(saver)
    }

    fun import(uri: Uri) {
        val reader = AndroidFileReader(context, uri)
        presentation.import(reader)
    }

    /*override val initialState: SettingsState = SettingsState()

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
                            Preference.Identifier.IMPORT_FONTO,
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
            Preference.Identifier.EXPORT_FONTO -> SettingsEffect.OpenExportStrategyPicker.emit()
            Preference.Identifier.IMPORT_FONTO -> SettingsEffect.OpenFilePicker("application/json").emit()
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

    fun startExporting(exportStrategy: ExportStrategy) {
        setState { copy(exportStrategy = exportStrategy) }
        SettingsEffect.ExportFonto.emit()
    }

    fun saveFontoBackupFile(uri: Uri) = viewModelScope.launch {
        setState { copy(isLoading = true) }

        val exportData = request { getExportData(state.exportStrategy) }
            .withErrorHandling {
                setState { copy(isLoading = false) }
                SettingsEffect.ShowMessage(StringResources.settings_export_fonto_data_preparation_failed).emit()
            }?.data ?: return@launch

        request { AndroidFileSaver(context, uri).save(exportData.toByteArray()) }
            .withErrorHandling {
                setState { copy(isLoading = false) }
                SettingsEffect.ShowMessage(StringResources.settings_export_fonto_file_saving_failed).emit()
            } ?: return@launch

        setState { copy(isLoading = false) }
        SettingsEffect.ShowMessage(StringResources.settings_export_fonto_successful).emit()
    }

    fun readFontoBackupFile(uri: Uri) = viewModelScope.launch {
        setState { copy(isLoading = true) }

        val fileReader = AndroidFileReader(context, uri)
        val backup = request { parseBackupData(fileReader) }
            .withErrorHandling {
                setState { copy(isLoading = false) }
                SettingsEffect.ShowMessage(StringResources.settings_import_fonto_file_reading_failed).emit()
            }?.data ?: return@launch

        request { importData(backup) }
            .withErrorHandling {
                setState { copy(isLoading = false) }
                SettingsEffect.ShowMessage(StringResources.settings_import_fonto_data_import_failed).emit()
            } ?: return@launch

        setState { copy(isLoading = false) }
        SettingsEffect.ShowMessage(StringResources.settings_import_fonto_successful).emit()
        eventBus.emit(AppEvent.RefreshFeed)
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
    }*/
}