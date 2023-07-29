package dev.weazyexe.fonto.common.app.notifications

import android.content.Context

internal actual class NotifierFactory(
    private val context: Context
) {

    actual fun create(): Notifier = AndroidNotifier(context)
}