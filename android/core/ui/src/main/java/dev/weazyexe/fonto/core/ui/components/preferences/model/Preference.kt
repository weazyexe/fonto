package dev.weazyexe.fonto.core.ui.components.preferences.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
sealed interface Preference {

    val id: Identifier

    @get:StringRes
    val title: Int

    @get:StringRes
    val subtitle: Int

    @get:DrawableRes
    val icon: Int

    @Immutable
    @Serializable
    data class Text(
        override val id: Identifier,
        override val title: Int,
        override val subtitle: Int,
        override val icon: Int
    ) : Preference

    @Immutable
    @Serializable
    data class Switch(
        override val id: Identifier,
        override val title: Int,
        override val subtitle: Int,
        override val icon: Int,
        val value: Boolean
    ) : Preference

    @Immutable
    @Serializable
    data class CustomValue<T>(
        override val id: Identifier,
        override val title: Int,
        override val subtitle: Int,
        override val icon: Int,
        val value: Value<T>,
        val possibleValues: List<Value<T>>
    ) : Preference

    @Serializable
    enum class Identifier {
        // Feed group
        MANAGE_FEED,
        MANAGE_CATEGORIES,
        OPEN_POST,

        // Display group
        THEME,
        DYNAMIC_COLORS,
        COLOR_SCHEME,

        // Backup
        EXPORT_FONTO,

        // Debug group
        DEBUG_MENU
    }
}