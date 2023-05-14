package dev.weazyexe.fonto.core.ui.components.preferences.model

import kotlinx.serialization.Serializable

@Serializable
data class Value<T>(
    val data: T,
    val title: String
)