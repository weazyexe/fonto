package dev.weazyexe.fonto.common.app.background

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dev.weazyexe.fonto.common.app.background.sync.SyncPostsAndroidWorker
import dev.weazyexe.fonto.common.app.background.sync.SyncPostsWorker
import io.github.aakira.napier.Napier
import java.time.Duration
import kotlin.reflect.KClass

internal class AndroidWorkManager(
    private val workManager: WorkManager
) : PlatformWorkManager {

    override fun <T : Worker> enqueue(workerClass: KClass<T>) {
        when (workerClass) {
            SyncPostsWorker::class -> enqueueSyncPostsWorker()
            else -> Napier.e(IllegalArgumentException("Unknown workerClass $workerClass")) { "" }
        }
    }

    override fun cancel(id: WorkerId) {
        workManager.cancelUniqueWork(id.name)
    }

    private fun enqueueSyncPostsWorker() {
        val duration = Duration.ofHours(1) // TODO add custom duration from settings

        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<SyncPostsAndroidWorker>(duration)
            .setConstraints(constraints)
            .setInitialDelay(duration)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WorkerId.SYNC_POSTS.name,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

        Napier.d { "Enqueued SyncPostWorker" }
    }
}