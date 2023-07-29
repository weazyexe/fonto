package dev.weazyexe.fonto.common.app.background

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dev.weazyexe.fonto.common.app.background.sync.SyncPostsAndroidWorker
import dev.weazyexe.fonto.common.feature.settings.SettingsStorage
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.Duration

internal class AndroidWorkManager(
    private val workManager: WorkManager,
    private val settingsStorage: SettingsStorage,
    private val scope: CoroutineScope
) : PlatformWorkManager {

    override fun enqueue(id: WorkerId) {
        scope.launch {
            when (id) {
                WorkerId.SYNC_POSTS -> enqueueSyncPostsWorker()
            }
        }
    }

    override fun cancel(id: WorkerId) {
        workManager.cancelUniqueWork(id.value)
    }

    private suspend fun enqueueSyncPostsWorker() {
        val syncInterval = settingsStorage.getSyncPostsInterval().hours
        val networkType = if (settingsStorage.shouldSyncIfMeteredConnection()) {
            NetworkType.CONNECTED
        } else {
            NetworkType.UNMETERED
        }
        val shouldSyncIfBatteryIsLow = settingsStorage.shouldSyncIfBatteryIsLow()

        val duration = Duration.ofMillis(15) // FIXME hardcoded
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(networkType)
            .setRequiresBatteryNotLow(!shouldSyncIfBatteryIsLow)
            .setRequiresStorageNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<SyncPostsAndroidWorker>(duration)
            .setConstraints(constraints)
            .setInitialDelay(duration)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WorkerId.SYNC_POSTS.value,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )

        Napier.d { "Enqueued SyncPostWorker" }
    }
}