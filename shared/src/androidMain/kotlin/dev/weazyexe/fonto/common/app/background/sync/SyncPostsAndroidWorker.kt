package dev.weazyexe.fonto.common.app.background.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dev.weazyexe.fonto.utils.asAndroidWorkerResult
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SyncPostsAndroidWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val task by inject<SyncPostsWorker>()

    override suspend fun doWork(): Result {
        return task.doWork().asAndroidWorkerResult()
    }
}