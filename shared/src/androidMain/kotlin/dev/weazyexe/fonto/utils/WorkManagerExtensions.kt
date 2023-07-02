package dev.weazyexe.fonto.utils

import androidx.work.ListenableWorker
import dev.weazyexe.fonto.common.app.background.WorkerResult

internal fun WorkerResult.asAndroidWorkerResult(): ListenableWorker.Result =
    when(this) {
        WorkerResult.SUCCESS -> ListenableWorker.Result.success()
        WorkerResult.FAILURE -> ListenableWorker.Result.failure()
        WorkerResult.RETRY -> ListenableWorker.Result.retry()
    }