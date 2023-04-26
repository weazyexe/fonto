package dev.weazyexe.fonto.ui.features.settings.screens.colorpicker

import dev.weazyexe.fonto.core.ui.components.preferences.model.Value
import kotlinx.serialization.Serializable

@Serializable
data class ColorPickerArgs(
    val colors: List<Value<Long>>
)