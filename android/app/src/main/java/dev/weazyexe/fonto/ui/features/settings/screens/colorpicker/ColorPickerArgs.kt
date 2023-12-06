package dev.weazyexe.fonto.ui.features.settings.screens.colorpicker

import dev.weazyexe.fonto.common.model.preference.ColorScheme
import kotlinx.serialization.Serializable

@Serializable
data class ColorPickerArgs(
    val selectedColor: ColorScheme,
    val colors: List<ColorScheme>
)
