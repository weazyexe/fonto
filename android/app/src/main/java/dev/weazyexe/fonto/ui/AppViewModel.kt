package dev.weazyexe.fonto.ui

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.bus.AppEvent
import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.feature.settings.SettingsStorage
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AppViewModel(
    private val settingsStorage: SettingsStorage,
    private val eventBus: EventBus
) : CoreViewModel<AppState, AppEffect>() {

    override val initialState: AppState = AppState()

    init {
        fetchSettings()
        listenToTheme()
    }

    private fun fetchSettings() = viewModelScope.launch {
        val theme = settingsStorage.getTheme()
        val dynamicColor = settingsStorage.isDynamicColorsEnabled()
        val accentColor = settingsStorage.getAccentColor()
        setState {
            copy(
                theme = theme,
                isDynamicColorsEnabled = dynamicColor,
                accentColor = accentColor ?: state.accentColor,
                isInitialized = true
            )
        }
    }

    private fun listenToTheme() = viewModelScope.launch {
        eventBus.observe()
            .onEach {
                when (it) {
                    is AppEvent.ThemeChanged -> setState { copy(theme = it.theme) }
                    is AppEvent.DynamicColorsChanged -> setState { copy(isDynamicColorsEnabled = it.isEnabled) }
                    is AppEvent.ColorSchemeChanged -> setState { copy(accentColor = it.color) }
                    else -> {
                        // Do nothing
                    }
                }
            }
            .collect()
    }
}