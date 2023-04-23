package dev.weazyexe.fonto.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.weazyexe.fonto.domain.OpenPostPreference
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class SettingsStorage(
    private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val openPostPreferenceKey = stringPreferencesKey(OPEN_POST_PREFERENCE)

    suspend fun getOpenPostPreference(): OpenPostPreference {
        val key = context.dataStore.data
            .map { it[openPostPreferenceKey] }
            .firstOrNull()
            ?: OpenPostPreference.INTERNAL.key

        return OpenPostPreference.byKey(key)
    }

    suspend fun setOpenPostPreference(pref: OpenPostPreference) {
        context.dataStore.edit {
            it[openPostPreferenceKey] = pref.key
        }
    }

    private companion object {

        const val OPEN_POST_PREFERENCE = "OPEN_POST_PREFERENCE"
    }
}

