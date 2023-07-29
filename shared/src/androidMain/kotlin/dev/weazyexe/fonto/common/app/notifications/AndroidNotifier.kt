package dev.weazyexe.fonto.common.app.notifications

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BigTextStyle
import androidx.core.app.NotificationManagerCompat
import dev.weazyexe.fonto.common.R
import dev.weazyexe.fonto.common.app.ACTION_MARK_AS_READ
import dev.weazyexe.fonto.common.app.EXTRA_NOTIFICATION_ID
import dev.weazyexe.fonto.common.app.broadcast.MarkAsReadBroadcastReceiver
import dev.weazyexe.fonto.common.model.notification.NotificationVisuals

class AndroidNotifier(
    private val context: Context
) : Notifier {

    @SuppressLint("MissingPermission")
    override fun notify(visuals: NotificationVisuals) {
        createNotificationChannel()

        val markAsReadPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, MarkAsReadBroadcastReceiver::class.java).apply {
                putExtra(EXTRA_NOTIFICATION_ID, visuals.id.origin)
                action = ACTION_MARK_AS_READ
            },
            PendingIntent.FLAG_IMMUTABLE
        )

        val id = context.getString(R.string.notification_channel_feed_updates_id)
        val notification = NotificationCompat.Builder(context, id)
            .setSmallIcon(R.drawable.ic_fonto_24)
            .setContentTitle(visuals.title)
            .setContentText(visuals.description)
            .setStyle(BigTextStyle().bigText(visuals.description))
            .addAction(
                R.drawable.ic_fonto_24,
                context.getString(R.string.notification_new_posts_mark_as_read),
                markAsReadPendingIntent
            )
            .build()

        if (context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(context).notify(visuals.id.hashCode(), notification)
        }
    }

    private fun createNotificationChannel() {
        val id = context.getString(R.string.notification_channel_feed_updates_id)
        val name = context.getString(R.string.notification_channel_feed_updates_title)
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel(id, name, importance)
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}