package dev.weazyexe.fonto.utils

import dev.weazyexe.fonto.common.BuildConfig

actual fun isReleaseBuild(): Boolean = BuildConfig.BUILD_TYPE == "release"