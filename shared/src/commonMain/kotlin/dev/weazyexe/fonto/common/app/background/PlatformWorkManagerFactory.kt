package dev.weazyexe.fonto.common.app.background

internal expect class PlatformWorkManagerFactory {

    fun create(): PlatformWorkManager
}

internal fun createPlatformWorkManager(factory: PlatformWorkManagerFactory): PlatformWorkManager {
    return factory.create()
}