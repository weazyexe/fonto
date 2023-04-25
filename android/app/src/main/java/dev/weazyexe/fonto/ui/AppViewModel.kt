package dev.weazyexe.fonto.ui

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.bus.AppEvent
import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.settings.SettingsStorage
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
        setState { copy(theme = theme, isDynamicColorsEnabled = dynamicColor) }
    }

    private fun listenToTheme() = viewModelScope.launch {
        eventBus.observe()
            .onEach {
                when (it) {
                    is AppEvent.ThemeChanged -> setState { copy(theme = it.theme) }
                    is AppEvent.DynamicColorsChanged -> setState { copy(isDynamicColorsEnabled = it.isEnabled) }
                    else -> {
                        // Do nothing
                    }
                }
            }
            .collect()
    }
}