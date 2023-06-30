package dev.weazyexe.fonto.app.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dev.weazyexe.fonto.common.app.background.sync.SyncPostsBackgroundTask

class SyncPostsWorker(
    private val syncPostsBackgroundTask: SyncPostsBackgroundTask,
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return Result.success()
    }
}