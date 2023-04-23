package dev.weazyexe.fonto.ui.features.settings.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Group(
    @StringRes val title: Int,
    val preferences: Collection<Preference>
)