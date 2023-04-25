package dev.weazyexe.fonto.common.model.preference

enum class Theme(val key: String) {
    LIGHT("LIGHT"),
    DARK("DARK"),
    SYSTEM("SYSTEM");

    companion object {

        fun byKey(key: String): Theme = values().first { it.key == key }
    }
}