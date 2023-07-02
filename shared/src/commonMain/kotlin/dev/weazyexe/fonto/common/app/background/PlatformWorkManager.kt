package dev.weazyexe.fonto.common.app.background

internal interface PlatformWorkManager {

    fun enqueue(id: WorkerId)

    fun cancel(id: WorkerId)
}