package dev.weazyexe.fonto.util

import dev.weazyexe.fonto.BuildConfig

object AppHelper {

    fun isReleaseBuild() = BuildConfig.BUILD_TYPE == "release"
}