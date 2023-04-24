package dev.weazyexe.fonto.ui.features.settings

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import dev.weazyexe.fonto.common.settings.SettingsStorage
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.ui.features.settings.model.Group
import dev.weazyexe.fonto.ui.features.settings.model.Preference
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsStorage: SettingsStorage
) : CoreViewModel<SettingsState, SettingsEffect>() {

    override val initialState: SettingsState = SettingsState()

    init {
        loadSettings()
    }

    fun loadSettings() = viewModelScope.launch {
        val openPostPreference = settingsStorage.getOpenPostPreference()
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
                else -> {
                    // Do nothing
                }
            }
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