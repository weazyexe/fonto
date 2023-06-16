package dev.weazyexe.fonto.common.model.preference

enum class ColorScheme(val argb: Long) : ValuePreference {
    BLUE(0xFF6383F8),
    GREEN(0xFF88CF9B),
    ORANGE(0xFFDFA576),
    RED(0xFFCE6E6E),
    PINK(0xFFFF96EA);

    companion object {

        fun byArgb(argb: Long): ColorScheme = values().first { it.argb == argb }
    }
}