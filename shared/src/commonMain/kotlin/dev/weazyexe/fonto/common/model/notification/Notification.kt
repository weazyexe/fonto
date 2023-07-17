package dev.weazyexe.fonto.common.model.notification

import dev.weazyexe.fonto.common.model.feed.Post

data class Notification(
    val id: Id,
    val newPosts: List<Post>,
    val isRead: Boolean
) {

    @JvmInline
    value class Id(val origin: Long)
}