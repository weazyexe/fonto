package dev.weazyexe.fonto.features.settings

import dev.weazyexe.fonto.common.app.background.WorkerId
import dev.weazyexe.fonto.common.data.bus.AppEvent
import dev.weazyexe.fonto.common.data.onError
import dev.weazyexe.fonto.common.data.onLoading
import dev.weazyexe.fonto.common.data.onSuccess
import dev.weazyexe.fonto.common.feature.backup.FileReader
import dev.weazyexe.fonto.common.feature.backup.FileSaver
import dev.weazyexe.fonto.common.model.backup.ExportStrategy
import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.Group
import dev.weazyexe.fonto.common.model.preference.Preference
import dev.weazyexe.fonto.common.model.preference.SyncPostsInterval
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.utils.feature.Feature
import dev.weazyexe.fonto.utils.isReleaseBuild
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class SettingsPresentationImpl(
    private val dependencies: SettingsDependencies
) : SettingsPresentation() {

    override val initialState: SettingsDomainState
        get() = dependencies.initialState

    override fun onCreate(scope: CoroutineScope) {
        super.onCreate(scope)
        loadSettings()
    }

    override fun onPreferenceClick(preference: Preference) {
        when (preference.key) {
            Preference.Key.MANAGE_FEED -> SettingsEffect.OpenManageFeedScreen.emit()

            Preference.Key.MANAGE_CATEGORIES -> SettingsEffect.OpenManageCategoriesScreen.emit()

            Preference.Key.NOTIFICATIONS -> SettingsEffect.OpenNotificationsScreen.emit()

            Preference.Key.OPEN_POST -> updatePreference(preference)

            Preference.Key.THEME ->
                SettingsEffect.OpenThemePicker(
                    currentTheme = (preference as Preference.Value<*>).value as Theme
                ).emit()

            Preference.Key.DYNAMIC_COLORS -> dynamicColorChanged(preference)

            Preference.Key.COLOR_SCHEME ->
                SettingsEffect.OpenColorSchemePicker(
                    currentColorScheme = (preference as Preference.Value<*>).value as ColorScheme
                ).emit()

            Preference.Key.SYNC_POSTS -> syncPostsChanged(preference)

            Preference.Key.SYNC_POSTS_INTERVAL ->
                SettingsEffect.OpenSyncIntervalPicker(
                    currentInterval = (preference as Preference.Value<*>).value as SyncPostsInterval
                ).emit()

            Preference.Key.SYNC_POSTS_IF_METERED_CONNECTION -> updatePreference(preference)
                .also { dependencies.platformWorkManager.enqueue(WorkerId.SYNC_POSTS) }

            Preference.Key.SYNC_POSTS_IF_BATTERY_IS_LOW -> updatePreference(preference)
                .also { dependencies.platformWorkManager.enqueue(WorkerId.SYNC_POSTS) }

            Preference.Key.EXPORT_FONTO -> SettingsEffect.OpenExportStrategyPicker.emit()

            Preference.Key.IMPORT_FONTO ->
                SettingsEffect.OpenImportFilePicker(
                    fileName = getBackupFileName(),
                    fileMimeType = "application/json"
                ).emit()

            Preference.Key.DEBUG_MENU -> SettingsEffect.OpenDebugScreen.emit()
        }
    }

    override fun onThemePicked(theme: Theme) {
        val preference = state.preferences
            .findPreference<Preference.Value<Theme>>(Preference.Key.THEME)
            ?: return

        dependencies.eventBus.emit(AppEvent.ThemeChanged(theme))
        updatePreference(preference.copy(value = theme))
    }

    override fun onColorSchemePicked(colorScheme: ColorScheme) {
        val preference = state.preferences
            .findPreference<Preference.Value<ColorScheme>>(Preference.Key.COLOR_SCHEME)
            ?: return

        dependencies.eventBus.emit(AppEvent.ColorSchemeChanged(colorScheme))
        updatePreference(preference.copy(value = colorScheme))
    }

    override fun onSyncIntervalPicked(interval: SyncPostsInterval) {
        val preference = state.preferences
            .findPreference<Preference.Value<SyncPostsInterval>>(Preference.Key.SYNC_POSTS_INTERVAL)
            ?: return

        dependencies.platformWorkManager.enqueue(WorkerId.SYNC_POSTS)
        updatePreference(preference.copy(value = interval))
    }

    override fun chooseExportFileDestination(strategy: ExportStrategy) {
        setState { copy(exportStrategy = strategy) }
        SettingsEffect.OpenExportFilePicker(fileName = getBackupFileName()).emit()
    }

    override fun export(saver: FileSaver) {
        dependencies.exportData(state.exportStrategy, saver)
            .onLoading { setState { copy(isLoading = true) } }
            .onError {
                setState { copy(isLoading = false) }
                SettingsEffect.ShowExportFailureMessage.emit()
            }
            .onSuccess {
                setState { copy(isLoading = false) }
                SettingsEffect.ShowExportSuccessMessage.emit()
            }
            .launchIn(scope)
    }

    override fun import(reader: FileReader) {
        dependencies.importData(reader)
            .onLoading { setState { copy(isLoading = true) } }
            .onError {
                setState { copy(isLoading = false) }
                SettingsEffect.ShowImportFailureMessage.emit()
            }
            .onSuccess {
                setState { copy(isLoading = false) }
                SettingsEffect.ShowImportSuccessMessage.emit()
                dependencies.eventBus.emit(AppEvent.RefreshFeed)
            }
            .launchIn(scope)
    }

    private fun dynamicColorChanged(preference: Preference) {
        val isEnabled = (preference as Preference.Switch).value
        dependencies.eventBus.emit(AppEvent.DynamicColorsChanged(isEnabled = isEnabled))
        updatePreference(preference)
    }

    private fun syncPostsChanged(preference: Preference) {
        val isEnabled = (preference as Preference.Switch).value

        if (isEnabled) {
            dependencies.platformWorkManager.enqueue(WorkerId.SYNC_POSTS)
        } else {
            dependencies.platformWorkManager.cancel(WorkerId.SYNC_POSTS)
        }

        updatePreference(preference)
    }

    private fun updatePreference(preference: Preference) {
        dependencies.savePreference(preference)
            .onSuccess { setState { copy(preferences = state.preferences.update(preference)) } }
            .launchIn(scope)
    }

    private fun loadSettings() {
        dependencies.getSettings()
            .onSuccess { setState { copy(preferences = it.data.updateGroupVisibility()) } }
            .launchIn(scope)
    }

    private fun getBackupFileName(): String {
        val currentTime = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .toString()

        return "fonto_backup_$currentTime.json"
    }

    private fun List<Group>.updateGroupVisibility() = map { group ->
        group.copy(preferences = group.preferences.updatePreferenceVisibility())
    }.filter { it.preferences.isNotEmpty() }

    private fun List<Preference>.updatePreferenceVisibility(): List<Preference> {
        val isDynamicColorsFeatureAvailable = dependencies
            .featureAvailabilityChecker
            .isFeatureAvailable(Feature.DYNAMIC_COLOR)

        val dynamicColorPreference =
            this.firstOrNull { it.key == Preference.Key.DYNAMIC_COLORS } as? Preference.Switch
        val shouldShowColorScheme =
            !isDynamicColorsFeatureAvailable || dynamicColorPreference?.value == false

        val isSyncEnabledPreference =
            this.firstOrNull { it.key == Preference.Key.SYNC_POSTS } as? Preference.Switch
        val shouldShowSyncSettings = isSyncEnabledPreference?.value == true

        return map {
            when (it.key) {
                Preference.Key.DYNAMIC_COLORS -> (it as Preference.Switch).copy(isVisible = isDynamicColorsFeatureAvailable)
                Preference.Key.COLOR_SCHEME -> (it as Preference.Value<*>).copy(isVisible = shouldShowColorScheme)
                Preference.Key.DEBUG_MENU -> (it as Preference.Text).copy(isVisible = !isReleaseBuild())
                Preference.Key.SYNC_POSTS_INTERVAL -> (it as Preference.Value<*>).copy(isVisible = shouldShowSyncSettings)
                Preference.Key.SYNC_POSTS_IF_METERED_CONNECTION -> (it as Preference.Switch).copy(
                    isVisible = shouldShowSyncSettings
                )

                Preference.Key.SYNC_POSTS_IF_BATTERY_IS_LOW -> (it as Preference.Switch).copy(
                    isVisible = shouldShowSyncSettings
                )

                else -> it
            }
        }
    }

    private fun List<Group>.update(preference: Preference) =
        map { group ->
            group.copy(
                preferences = group.preferences
                    .map { pref ->
                        if (pref.key == preference.key) {
                            preference
                        } else {
                            pref
                        }
                    }
                    .updatePreferenceVisibility()
            )
        }.filter { it.preferences.isNotEmpty() }

    private inline fun <reified T : Preference> List<Group>.findPreference(key: Preference.Key): T? =
        flatMap { it.preferences }.firstOrNull { it.key == key } as? T
}