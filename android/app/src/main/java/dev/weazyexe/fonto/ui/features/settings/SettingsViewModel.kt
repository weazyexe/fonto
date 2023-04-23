package dev.weazyexe.fonto.ui.features.settings

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import dev.weazyexe.fonto.data.SettingsStorage
import dev.weazyexe.fonto.domain.OpenPostPreference
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
        setState { copy(openPostPreference = openPostPreference) }
    }

    fun onOpenPostPreferenceChange(value: Boolean) = viewModelScope.launch {
        val newValue = if (value) {
            OpenPostPreference.INTERNAL
        } else {
            OpenPostPreference.DEFAULT_BROWSER
        }
        settingsStorage.setOpenPostPreference(newValue)
        setState { copy(openPostPreference = newValue) }
    }
}