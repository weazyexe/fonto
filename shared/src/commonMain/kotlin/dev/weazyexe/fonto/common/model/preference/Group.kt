package dev.weazyexe.fonto.common.model.preference

data class Group(
    val key: Key,
    val preferences: List<Preference>
) {

    enum class Key {
        FEED,
        APPEARANCE,
        SYNC,
        BACKUP,
        DEBUG
    }
}