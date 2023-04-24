package dev.weazyexe.fonto.common.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class AndroidSettingsStorage(
    private val context: Context
) : SettingsStorage {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val openPostPreferenceKey = stringPreferencesKey(OPEN_POST_PREFERENCE)

    override suspend fun getOpenPostPreference(): OpenPostPreference {
        val key = context.dataStore.data
            .map { it[openPostPreferenceKey] }
            .firstOrNull()
            ?: OpenPostPreference.INTERNAL.key

        return OpenPostPreference.byKey(key)
    }

    override suspend fun saveOpenPostPreference(preference: OpenPostPreference) {
        context.dataStore.edit {
            it[openPostPreferenceKey] = preference.key
        }
    }

    companion object {

        const val OPEN_POST_PREFERENCE = "OPEN_POST_PREFERENCE"
    }
}