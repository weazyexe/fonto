package dev.weazyexe.fonto.core.ui.components.preferences.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Group(
    @StringRes val title: Int,
    val preferences: Collection<Preference>
)

fun List<Group>.findPreference(id: Preference.Identifier): Preference? =
    this.flatMap { it.preferences }.firstOrNull { it.id == id }