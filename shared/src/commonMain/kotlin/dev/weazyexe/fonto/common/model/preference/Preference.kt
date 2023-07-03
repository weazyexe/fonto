package dev.weazyexe.fonto.common.model.preference

sealed interface Preference {

    val key: Key
    val isVisible: Boolean

    data class Text(
        override val key: Key,
        override val isVisible: Boolean = true
    ) : Preference

    data class Switch(
        override val key: Key,
        override val isVisible: Boolean = true,
        val value: Boolean
    ) : Preference

    data class Value<T : ValuePreference>(
        override val key: Key,
        override val isVisible: Boolean = true,
        val value: T,
        val possibleValues: List<T>
    ) : Preference

    enum class Key {
        // Feed group
        MANAGE_FEED,
        MANAGE_CATEGORIES,
        NOTIFICATIONS,
        OPEN_POST,

        // Appearance
        THEME,
        DYNAMIC_COLORS,
        COLOR_SCHEME,

        // Sync
        SYNC_POSTS,
        SYNC_POSTS_INTERVAL,
        SYNC_POSTS_IF_METERED_CONNECTION,
        SYNC_POSTS_IF_BATTERY_IS_LOW,

        // Backup
        EXPORT_FONTO,
        IMPORT_FONTO,

        // Debug group
        DEBUG_MENU
    }
}