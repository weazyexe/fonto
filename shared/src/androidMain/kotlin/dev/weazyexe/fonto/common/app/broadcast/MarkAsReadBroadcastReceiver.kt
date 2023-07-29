package dev.weazyexe.fonto.common.app.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import dev.weazyexe.fonto.common.app.ACTION_MARK_AS_READ
import dev.weazyexe.fonto.common.app.EXTRA_NOTIFICATION_ID
import dev.weazyexe.fonto.common.app.notifications.markasread.MarkAsReadActionHandler
import dev.weazyexe.fonto.common.model.notification.Notification
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MarkAsReadBroadcastReceiver : BroadcastReceiver(), KoinComponent {

    private val markAsReadActionHandler by inject<MarkAsReadActionHandler>()

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != ACTION_MARK_AS_READ) return
        val id = intent.getStringExtra(EXTRA_NOTIFICATION_ID)?.let { Notification.Id(it) } ?: return
        markAsReadActionHandler.handle(id)
        NotificationManagerCompat.from(context).cancel(id.hashCode())
    }
}