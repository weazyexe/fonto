package dev.weazyexe.fonto.common.feature.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.weazyexe.fonto.common.model.preference.ColorScheme
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import dev.weazyexe.fonto.common.model.preference.Theme
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

internal class AndroidSettingsStorage(
    private val context: Context
) : SettingsStorage {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val openPostPreferenceKey = stringPreferencesKey(OPEN_POST_PREFERENCE)
    private val themePreferenceKey = stringPreferencesKey(THEME_PREFERENCE)
    private val dynamicColorsPreferenceKey = booleanPreferencesKey(DYNAMIC_COLORS_PREFERENCE)
    private val accentColorPreferenceKey = longPreferencesKey(ACCENT_COLOR_PREFERENCE)
    private val appInitializedPreferenceKey = booleanPreferencesKey(APP_INITIALIZED_PREFERENCE)

    override suspend fun getOpenPostPreference(): OpenPostPreference {
        val key = get(key = openPostPreferenceKey, default = OpenPostPreference.INTERNAL.key)
        return OpenPostPreference.byKey(key)
    }

    override suspend fun saveOpenPostPreference(preference: OpenPostPreference) {
        save(key = openPostPreferenceKey, value = preference.key)
    }

    override suspend fun getTheme(): Theme {
        val key = get(key = themePreferenceKey, default = Theme.SYSTEM.key)
        return Theme.byKey(key)
    }

    override suspend fun saveTheme(theme: Theme) {
        save(key = themePreferenceKey, value = theme.key)
    }

    override suspend fun isDynamicColorsEnabled(): Boolean {
        return get(key = dynamicColorsPreferenceKey, default = true)
    }

    override suspend fun saveDynamicColorsEnabled(isEnabled: Boolean) {
        save(key = dynamicColorsPreferenceKey, value = isEnabled)
    }

    override suspend fun getAccentColor(): ColorScheme {
        val color = get(key = accentColorPreferenceKey, default = ColorScheme.BLUE.argb)
        return ColorScheme.byArgb(color)
    }

    override suspend fun saveAccentColor(color: ColorScheme) {
        save(key = accentColorPreferenceKey, value = color.argb)
    }

    override suspend fun isAppInitialized(): Boolean {
        return get(appInitializedPreferenceKey, default = false)
    }

    override suspend fun saveAppInitialized(isInitialized: Boolean) {
        save(appInitializedPreferenceKey, isInitialized)
    }

    private suspend fun <T> get(key: Preferences.Key<T>, default: T): T =
        context.dataStore.data
            .map { it[key] }
            .firstOrNull()
            ?: default

    private suspend fun <T> save(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    companion object {

        const val OPEN_POST_PREFERENCE = "OPEN_POST_PREFERENCE"
        const val THEME_PREFERENCE = "THEME_PREFERENCE"
        const val DYNAMIC_COLORS_PREFERENCE = "DYNAMIC_COLORS_PREFERENCE"
        const val ACCENT_COLOR_PREFERENCE = "ACCENT_COLOR_PREFERENCE"
        const val APP_INITIALIZED_PREFERENCE = "APP_INITIALIZED_PREFERENCE"
    }
}