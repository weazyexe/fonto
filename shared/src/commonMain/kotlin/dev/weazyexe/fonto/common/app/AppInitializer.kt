package dev.weazyexe.fonto.common.app

interface AppInitializer : Initializer<AppInitializer.Args> {

    data class Args(
        val areMockFeedsEnabled: Boolean
    )
}