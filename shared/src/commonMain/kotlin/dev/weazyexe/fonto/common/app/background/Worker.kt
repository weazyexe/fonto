package dev.weazyexe.fonto.common.app.background

internal interface Worker {

    suspend fun doWork() : WorkerResult
}