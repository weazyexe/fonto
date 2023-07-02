package dev.weazyexe.fonto.common.app.background

import androidx.work.WorkManager
import dev.weazyexe.fonto.common.feature.settings.SettingsStorage
import kotlinx.coroutines.CoroutineScope

internal actual class PlatformWorkManagerFactory(
    private val workManager: WorkManager,
    private val settingsStorage: SettingsStorage,
    private val scope: CoroutineScope
) {

    actual fun create(): PlatformWorkManager =
        AndroidWorkManager(workManager, settingsStorage, scope)
}