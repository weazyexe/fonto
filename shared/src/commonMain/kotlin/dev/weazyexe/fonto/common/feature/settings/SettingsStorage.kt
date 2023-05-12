package dev.weazyexe.fonto.common.feature.settings

import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import dev.weazyexe.fonto.common.model.preference.Theme

interface SettingsStorage {

    suspend fun getOpenPostPreference(): OpenPostPreference

    suspend fun saveOpenPostPreference(preference: OpenPostPreference)

    suspend fun getTheme(): Theme

    suspend fun saveTheme(theme: Theme)

    suspend fun isDynamicColorsEnabled(): Boolean

    suspend fun saveDynamicColorsEnabled(isEnabled: Boolean)

    suspend fun getAccentColor(): Long?

    suspend fun saveAccentColor(color: Long)
}