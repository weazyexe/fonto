package dev.weazyexe.fonto.ui

import androidx.lifecycle.viewModelScope
import dev.weazyexe.fonto.common.data.bus.AppEvent
import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.settings.SettingsStorage
import dev.weazyexe.fonto.core.ui.presentation.CoreViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AppViewModel(
    private val settingsStorage: SettingsStorage,
    private val eventBus: EventBus
) : CoreViewModel<AppState, AppEffect>() {

    override val initialState: AppState = AppState()

    init {
        fetchTheme()
        listenToTheme()
    }

    private fun fetchTheme() = viewModelScope.launch {
        val theme = settingsStorage.getTheme()
        setState { copy(theme = theme) }
    }

    private fun listenToTheme() = viewModelScope.launch {
        eventBus.observe()
            .filterIsInstance<AppEvent.ThemeChanged>()
            .onEach { setState { copy(theme = it.theme) } }
            .collect()
    }
}