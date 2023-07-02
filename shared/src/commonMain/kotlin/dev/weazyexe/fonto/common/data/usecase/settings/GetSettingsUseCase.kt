package dev.weazyexe.fonto.common.data.usecase.settings

import dev.weazyexe.fonto.common.data.AsyncResult
import dev.weazyexe.fonto.common.feature.settings.SettingsStorage
import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.Group
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import dev.weazyexe.fonto.common.model.preference.Preference
import dev.weazyexe.fonto.common.model.preference.SyncPostsInterval
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.utils.extensions.flowIo
import kotlinx.coroutines.flow.Flow

internal class GetSettingsUseCase(
    private val settingsStorage: SettingsStorage,
    private val getDefaultSettings: GetDefaultSettingsUseCase
) {

    operator fun invoke(): Flow<AsyncResult<List<Group>>> = flowIo {
        emit(AsyncResult.Loading())

        val openPostPreference = settingsStorage.getOpenPostPreference()
        val themePreference = settingsStorage.getTheme()
        val isDynamicColorsEnabled = settingsStorage.isDynamicColorsEnabled()
        val accentColor = settingsStorage.getAccentColor()
        val isSyncPostsEnabled = settingsStorage.isSyncPostsEnabled()
        val syncPostsInterval = settingsStorage.getSyncPostsInterval()
        val shouldSyncIfMeteredConnection = settingsStorage.shouldSyncIfMeteredConnection()
        val shouldSyncIfBatteryIsLow = settingsStorage.shouldSyncIfBatteryIsLow()

        val preferencesWithValues = getDefaultSettings().map { group ->
            group.copy(preferences = group.preferences.map { preference ->
                when (preference.key) {
                    Preference.Key.OPEN_POST -> {
                        Preference.Switch(
                            key = Preference.Key.OPEN_POST,
                            value = openPostPreference == OpenPostPreference.INTERNAL
                        )
                    }

                    Preference.Key.THEME -> {
                        Preference.Value(
                            key = Preference.Key.THEME,
                            value = themePreference,
                            possibleValues = Theme.values().toList()
                        )
                    }

                    Preference.Key.DYNAMIC_COLORS -> {
                        Preference.Switch(
                            key = Preference.Key.DYNAMIC_COLORS,
                            value = isDynamicColorsEnabled
                        )
                    }

                    Preference.Key.COLOR_SCHEME -> {
                        Preference.Value(
                            key = Preference.Key.COLOR_SCHEME,
                            value = accentColor,
                            possibleValues = ColorScheme.values().toList()
                        )
                    }

                    Preference.Key.SYNC_POSTS -> {
                        Preference.Switch(
                            key = Preference.Key.SYNC_POSTS,
                            value = isSyncPostsEnabled
                        )
                    }

                    Preference.Key.SYNC_POSTS_INTERVAL -> {
                        Preference.Value(
                            key = Preference.Key.SYNC_POSTS_INTERVAL,
                            value = syncPostsInterval,
                            possibleValues = SyncPostsInterval.values().toList()
                        )
                    }

                    Preference.Key.SYNC_POSTS_IF_METERED_CONNECTION -> {
                        Preference.Switch(
                            key = Preference.Key.SYNC_POSTS_IF_METERED_CONNECTION,
                            value = shouldSyncIfMeteredConnection
                        )
                    }

                    Preference.Key.SYNC_POSTS_IF_BATTERY_IS_LOW -> {
                        Preference.Switch(
                            key = Preference.Key.SYNC_POSTS_IF_BATTERY_IS_LOW,
                            value = shouldSyncIfBatteryIsLow
                        )
                    }

                    Preference.Key.MANAGE_FEED,
                    Preference.Key.MANAGE_CATEGORIES,
                    Preference.Key.EXPORT_FONTO,
                    Preference.Key.IMPORT_FONTO,
                    Preference.Key.DEBUG_MENU -> preference
                }
            })
        }

        emit(AsyncResult.Success(preferencesWithValues))
    }
}