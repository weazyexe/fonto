package dev.weazyexe.fonto.features.settings

import dev.weazyexe.fonto.common.data.bus.EventBus
import dev.weazyexe.fonto.common.data.usecase.backup.ExportDataUseCase
import dev.weazyexe.fonto.common.data.usecase.backup.ImportDataUseCase
import dev.weazyexe.fonto.common.data.usecase.settings.GetSettingsUseCase
import dev.weazyexe.fonto.common.data.usecase.settings.SavePreferenceUseCase
import dev.weazyexe.fonto.utils.feature.FeatureAvailabilityChecker

internal data class SettingsDependencies(
    val initialState: SettingsDomainState,

    val getSettings: GetSettingsUseCase,
    val exportData: ExportDataUseCase,
    val importData: ImportDataUseCase,
    val savePreference: SavePreferenceUseCase,

    val eventBus: EventBus,
    val featureAvailabilityChecker: FeatureAvailabilityChecker
)
