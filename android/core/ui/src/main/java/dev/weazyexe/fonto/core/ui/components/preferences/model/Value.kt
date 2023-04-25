package dev.weazyexe.fonto.core.ui.components.preferences.model

import androidx.annotation.StringRes
import kotlinx.serialization.Serializable

@Serializable
data class Value<T>(
    val data: T,
    @StringRes val title: Int
)