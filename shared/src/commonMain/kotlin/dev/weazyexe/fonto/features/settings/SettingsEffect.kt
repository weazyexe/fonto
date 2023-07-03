package dev.weazyexe.fonto.features.settings

import dev.weazyexe.fonto.arch.Effect
import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.SyncPostsInterval
import dev.weazyexe.fonto.common.model.preference.Theme

sealed interface SettingsEffect : Effect {

    object OpenManageFeedScreen : SettingsEffect

    object OpenManageCategoriesScreen : SettingsEffect

    object OpenNotificationsScreen : SettingsEffect

    data class OpenThemePicker(val currentTheme: Theme) : SettingsEffect

    data class OpenColorSchemePicker(val currentColorScheme: ColorScheme) : SettingsEffect

    data class OpenSyncIntervalPicker(val currentInterval: SyncPostsInterval) : SettingsEffect

    object OpenExportStrategyPicker : SettingsEffect

    data class OpenImportFilePicker(
        val fileName: String,
        val fileMimeType: String
    ) : SettingsEffect

    data class OpenExportFilePicker(val fileName: String) : SettingsEffect

    object OpenDebugScreen : SettingsEffect

    object ShowExportFailureMessage : SettingsEffect

    object ShowExportSuccessMessage : SettingsEffect

    object ShowImportFailureMessage : SettingsEffect

    object ShowImportSuccessMessage : SettingsEffect
}