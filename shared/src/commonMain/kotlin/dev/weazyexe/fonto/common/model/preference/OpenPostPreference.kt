package dev.weazyexe.fonto.common.model.preference

enum class OpenPostPreference(val key: String) {
    DEFAULT_BROWSER("DEFAULT_BROWSER"),
    INTERNAL("INTERNAL");

    companion object {

        fun byKey(key: String): OpenPostPreference =
            values().first { it.key == key }
    }
}