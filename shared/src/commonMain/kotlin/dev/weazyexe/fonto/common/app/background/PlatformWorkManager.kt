package dev.weazyexe.fonto.common.app.background

import kotlin.reflect.KClass

internal interface PlatformWorkManager {

    fun <T: Worker> enqueue(workerClass: KClass<T>)

    fun cancel(id: WorkerId)
}