package dev.weazyexe.fonto.common.app.background

interface BackgroundTask {

    suspend fun doWork() : BackgroundTaskResult
}