package dev.weazyexe.fonto.common.feature.settings

import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import dev.weazyexe.fonto.common.model.preference.Theme

internal interface SettingsStorage {

    suspend fun getOpenPostPreference(): OpenPostPreference

    suspend fun saveOpenPostPreference(preference: OpenPostPreference)

    suspend fun getTheme(): Theme

    suspend fun saveTheme(theme: Theme)

    suspend fun isDynamicColorsEnabled(): Boolean

    suspend fun saveDynamicColorsEnabled(isEnabled: Boolean)

    suspend fun getAccentColor(): ColorScheme

    suspend fun saveAccentColor(color: ColorScheme)

    suspend fun isAppInitialized(): Boolean

    suspend fun saveAppInitialized(isInitialized: Boolean)

    suspend fun isSyncPostsEnabled() : Boolean

    suspend fun saveSyncPostsEnabled(value: Boolean)


}