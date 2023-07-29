package dev.weazyexe.fonto.common.model.notification

import dev.weazyexe.fonto.common.app.notifications.NotificationAction

data class NotificationVisuals(
    val id: Notification.Id,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val actions: List<NotificationAction>
)