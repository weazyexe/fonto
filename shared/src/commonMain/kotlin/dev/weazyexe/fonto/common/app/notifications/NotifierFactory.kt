package dev.weazyexe.fonto.common.app.notifications

internal expect class NotifierFactory {

    fun create(): Notifier
}

internal fun createNotifier(factory: NotifierFactory): Notifier {
    return factory.create()
}