package dev.weazyexe.fonto.common.model.notification

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val id: Id,
    val type: Type,
    val isRead: Boolean,
    val createdAt: Instant,
    val meta: NotificationMeta
) {

    @Serializable
    @JvmInline
    value class Id(val origin: String)

    enum class Type(val origin: String) {
        NEW_POSTS("NEW_POSTS")
    }
}