package dev.weazyexe.fonto.common.app.initializer

interface AppInitializer : Initializer<AppInitializer.Args> {

    data class Args(
        val areMockFeedsEnabled: Boolean
    )
}