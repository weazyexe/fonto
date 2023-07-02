package dev.weazyexe.fonto.common.app.background

interface Worker {

    suspend fun doWork() : WorkerResult
}