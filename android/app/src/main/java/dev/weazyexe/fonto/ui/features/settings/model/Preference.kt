package dev.weazyexe.fonto.ui.features.settings.model

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
        val value: T,
        val possibleValues: Collection<T>
    ) : Preference

    @Serializable
    enum class Identifier {
        // Feed group
        MANAGE_FEED,
        OPEN_POST,

        // Debug group
        DEBUG_MENU
    }
}