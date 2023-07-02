package dev.weazyexe.fonto.common.data.usecase.settings

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.feature.settings.SettingsStorage
import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import dev.weazyexe.fonto.common.model.preference.Preference
import dev.weazyexe.fonto.common.model.preference.SyncPostsInterval
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

internal class SavePreferenceUseCase(
    private val settingsStorage: SettingsStorage
) {

    operator fun invoke(preference: Preference): Flow<AsyncResult<Unit>> = flowIo {
        emit(AsyncResult.Loading())

        when (preference.key) {
            Preference.Key.OPEN_POST -> {
                val isSelected = (preference as Preference.Switch).value
                settingsStorage.saveOpenPostPreference(
                    if (isSelected) {
                        OpenPostPreference.INTERNAL
                    } else {
                        OpenPostPreference.DEFAULT_BROWSER
                    }
                )
            }

            Preference.Key.THEME -> {
                val theme = (preference as Preference.Value<*>).value as Theme
                settingsStorage.saveTheme(theme)
            }

            Preference.Key.DYNAMIC_COLORS -> {
                val isDynamicColorsEnabled = (preference as Preference.Switch).value
                settingsStorage.saveDynamicColorsEnabled(isDynamicColorsEnabled)
            }

            Preference.Key.COLOR_SCHEME -> {
                val colorScheme = (preference as Preference.Value<*>).value as ColorScheme
                settingsStorage.saveAccentColor(colorScheme)
            }

            Preference.Key.SYNC_POSTS -> {
                val isSyncPostsEnabled = (preference as Preference.Switch).value
                settingsStorage.saveSyncPostsEnabled(isSyncPostsEnabled)
            }

            Preference.Key.SYNC_POSTS_INTERVAL -> {
                val interval = (preference as Preference.Value<*>).value as SyncPostsInterval
                settingsStorage.saveSyncPostsInterval(interval)
            }

            Preference.Key.SYNC_POSTS_IF_METERED_CONNECTION -> {
                val shouldSyncIfMeteredConnection = (preference as Preference.Switch).value
                settingsStorage.saveSyncIfMeteredConnection(shouldSyncIfMeteredConnection)
            }

            Preference.Key.SYNC_POSTS_IF_BATTERY_IS_LOW -> {
                val shouldSyncIfBatteryIsLow = (preference as Preference.Switch).value
                settingsStorage.saveSyncIfBatteryIsLow(shouldSyncIfBatteryIsLow)
            }

            else -> throw IllegalArgumentException("This preference is not supported for saving")
        }

        emit(AsyncResult.Success(Unit))
    }
}