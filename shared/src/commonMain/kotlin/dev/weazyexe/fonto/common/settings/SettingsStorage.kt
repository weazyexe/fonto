package dev.weazyexe.fonto.common.settings

import dev.weazyexe.fonto.common.model.preference.OpenPostPreference

interface SettingsStorage {

    suspend fun getOpenPostPreference(): OpenPostPreference

    suspend fun saveOpenPostPreference(preference: OpenPostPreference)
}