package dev.weazyexe.fonto.ui.features.settings.screens.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.weazyexe.fonto.common.model.preference.OpenPostPreference
import dev.weazyexe.fonto.common.model.preference.Theme
import dev.weazyexe.fonto.core.ui.components.preferences.model.Group
import dev.weazyexe.fonto.core.ui.components.preferences.model.Preference
import dev.weazyexe.fonto.core.ui.components.preferences.model.Value
import dev.weazyexe.fonto.core.ui.presentation.Effect
import dev.weazyexe.fonto.core.ui.presentation.State

data class SettingsState(
    val preferences: List<Group> = emptyList(),
    val openPostPreference: OpenPostPreference = OpenPostPreference.INTERNAL,
    val hiddenPreferences: List<Preference.Identifier> = emptyList(),
    val isLoading: Boolean = false
) : State

sealed interface SettingsEffect : Effect {

    object OpenManageFeedScreen : SettingsEffect

    object OpenCategoriesScreen : SettingsEffect

    object OpenDebugScreen : SettingsEffect

    data class ShowMessage(@StringRes val message: Int) : SettingsEffect

    object ExportFonto : SettingsEffect

    data class OpenThemePickerDialog(
        val id: Preference.Identifier,
        @StringRes val title: Int,
        @DrawableRes val icon: Int,
        val value: Value<Theme>,
        val possibleValues: List<Value<Theme>>
    ) : SettingsEffect

    data class OpenColorPickerDialog(
        val id: Preference.Identifier,
        val value: Value<Long>,
        val possibleValues: List<Value<Long>>
    ) : SettingsEffect
}
