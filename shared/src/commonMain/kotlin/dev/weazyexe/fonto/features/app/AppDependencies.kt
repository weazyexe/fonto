package dev.weazyexe.fonto.features.app

import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.feature.settings.SettingsStorage

internal data class AppDependencies(
    val initialState: AppDomainState,
    val eventBus: EventBus,
    val settingsStorage: SettingsStorage
)