package dev.weazyexe.fonto.ui.features.settings.screens.settings.viewstate

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import dev.weazyexe.fonto.common.model.preference.Preference
import dev.weazyexe.fonto.common.model.preference.ValuePreference

@Immutable
sealed interface PreferenceViewState {

    val key: Preference.Key
    val title: String
    val description: String
    @get:DrawableRes
    val icon: Int
    val isVisible: Boolean

    @Immutable
    data class Text(
        override val key: Preference.Key,
        override val title: String,
        override val description: String,
        override val icon: Int,
        override val isVisible: Boolean
    ) : PreferenceViewState

    @Immutable
    data class Switch(
        override val key: Preference.Key,
        override val title: String,
        override val description: String,
        override val icon: Int,
        override val isVisible: Boolean,
        val value: Boolean
    ) : PreferenceViewState

    @Immutable
    data class Value<T: ValuePreference>(
        override val key: Preference.Key,
        override val title: String,
        override val description: String,
        override val icon: Int,
        override val isVisible: Boolean,
        val value: T,
        val possibleValues: List<T>,
        val displayValue: String
    ) : PreferenceViewState
}
