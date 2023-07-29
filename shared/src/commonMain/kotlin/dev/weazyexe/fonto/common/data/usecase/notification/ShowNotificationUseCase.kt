package dev.weazyexe.fonto.common.data.usecase.notification

import dev.weazyexe.fonto.common.app.notifications.Notifier
import dev.weazyexe.fonto.common.model.notification.Notification
import dev.weazyexe.fonto.common.model.notification.NotificationMeta
import dev.weazyexe.fonto.common.model.notification.NotificationVisuals

internal class ShowNotificationUseCase(
    private val createNewPostsNotificationVisuals: CreateNewPostsNotificationVisualsUseCase,
    private val notifier: Notifier
) {

    operator fun invoke(notification: Notification) {
        val visuals = notification.asNotificationVisuals()
        notifier.notify(visuals)
    }

    private fun Notification.asNotificationVisuals(): NotificationVisuals =
        when (meta) {
            is NotificationMeta.NewPosts ->
                createNewPostsNotificationVisuals(id, meta)
        }
}