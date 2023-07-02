package dev.weazyexe.fonto.common.app.background

import androidx.work.WorkManager

internal actual class PlatformWorkManagerFactory(
    private val workManager: WorkManager
) {

    actual fun create(): PlatformWorkManager = AndroidWorkManager(workManager)
}