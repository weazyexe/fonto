package dev.weazyexe.fonto.ui.features.settings.screens.settings

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.bus.AppEvent
import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.common.settings.SettingsStorage
import dev.weazyexe.fonto.core.ui.R
import dev.weazyexe.fonto.core.ui.components.preferences.model.Group
import dev.weazyexe.fonto.core.ui.components.preferences.model.Preference
import dev.weazyexe.fonto.core.ui.components.preferences.model.Value
import dev.weazyexe.fonto.core.ui.components.preferences.model.findPreference
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.core.ui.theme.COLORS
import dev.weazyexe.fonto.core.ui.theme.DEFAULT_COLOR
import dev.weazyexe.fonto.core.ui.theme.asColorValue
import dev.weazyexe.fonto.util.stringRes
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsStorage: SettingsStorage,
    private val eventBus: EventBus
) : CoreViewModel<SettingsState, SettingsEffect>() {

    override val initialState: SettingsState = SettingsState()

    init {
        loadSettings()
    }

    fun loadSettings() = viewModelScope.launch {
        val openPostPreference = settingsStorage.getOpenPostPreference()
        val themePreference = settingsStorage.getTheme()
        val isDynamicColorsEnabled = settingsStorage.isDynamicColorsEnabled()
        val accentColor = settingsStorage.getAccentColor()?.asColorValue() ?: DEFAULT_COLOR

        val updatedPreferences = state.preferences
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
                                    value = Value(themePreference, themePreference.stringRes),
                                    possibleValues = Theme.values().map { Value(it, it.stringRes) }
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
                                    possibleValues = COLORS
                                )
                            }

                            Preference.Identifier.MANAGE_FEED,
                            Preference.Identifier.DEBUG_MENU -> preference
                        }
                    }
                )
            }

        setState { copy(preferences = updatedPreferences) }
    }

    fun onTextPreferenceClick(preference: Preference.Text) {
        when (preference.id) {
            Preference.Identifier.MANAGE_FEED -> SettingsEffect.OpenManageFeedScreen.emit()
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
                Preference.Identifier.DYNAMIC_COLORS -> onDynamicColorsEnabledChanged(preference, value)
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
                    title = R.string.settings_display_theme_title,
                    icon = R.drawable.ic_lightbulb_24,
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

        update(preference.copy(value = Value(theme, theme.stringRes)))
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

    private suspend fun onDynamicColorsEnabledChanged(preference: Preference.Switch, value: Boolean) {
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
        setState { copy(preferences = updatedPreferences) }
    }
}