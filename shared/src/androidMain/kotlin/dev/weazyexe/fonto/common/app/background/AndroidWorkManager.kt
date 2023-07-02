package dev.weazyexe.fonto.common.app.background

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dev.weazyexe.fonto.common.app.background.sync.SyncPostsAndroidWorker
import io.github.aakira.napier.Napier
import java.time.Duration

internal class AndroidWorkManager(
    private val workManager: WorkManager
) : PlatformWorkManager {

    override fun enqueue(id: WorkerId) {
        when (id) {
            WorkerId.SYNC_POSTS -> enqueueSyncPostsWorker()
        }
    }

    override fun cancel(id: WorkerId) {
        workManager.cancelUniqueWork(id.value)
    }

    private fun enqueueSyncPostsWorker() {
        val duration = Duration.ofHours(1) // TODO add custom duration from settings
        val workRequest = PeriodicWorkRequestBuilder<SyncPostsAndroidWorker>(duration)
            .setInitialDelay(duration)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WorkerId.SYNC_POSTS.value,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

        Napier.d { "Enqueued SyncPostWorker" }
    }
}