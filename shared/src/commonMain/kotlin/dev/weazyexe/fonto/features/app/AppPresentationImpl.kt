package dev.weazyexe.fonto.features.app

import dev.weazyexe.fonto.common.app.background.WorkerId
import dev.weazyexe.fonto.common.app.background.sync.SyncPostsWorker
import dev.weazyexe.fonto.common.data.bus.AppEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class AppPresentationImpl(
    private val dependencies: AppDependencies
) : AppPresentation() {

    override val initialState: AppDomainState
        get() = dependencies.initialState

    override fun onCreate(scope: CoroutineScope) {
        super.onCreate(scope)
        fetchSettings()
        listenToEventBus()
    }

    private fun fetchSettings() = scope.launch {
        val theme = dependencies.settingsStorage.getTheme()
        val dynamicColor = dependencies.settingsStorage.isDynamicColorsEnabled()
        val accentColor = dependencies.settingsStorage.getAccentColor()
        setState {
            copy(
                theme = theme,
                isDynamicColorsEnabled = dynamicColor,
                accentColor = accentColor,
                isInitialized = true
            )
        }
    }

    private fun listenToEventBus() = scope.launch {
        dependencies.eventBus.observe()
            .onEach {
                when (it) {
                    is AppEvent.ThemeChanged -> setState { copy(theme = it.theme) }

                    is AppEvent.DynamicColorsChanged -> setState { copy(isDynamicColorsEnabled = it.isEnabled) }

                    is AppEvent.ColorSchemeChanged -> setState { copy(accentColor = it.color) }

                    is AppEvent.StartSyncPostsBackgroundTask ->
                        dependencies.platformWorkManager.enqueue(SyncPostsWorker::class)

                    is AppEvent.StopSyncPostsBackgroundTask ->
                        dependencies.platformWorkManager.cancel(WorkerId.SYNC_POSTS)

                    else -> {
                        // Do nothing
                    }
                }
            }
            .collect()
    }
}