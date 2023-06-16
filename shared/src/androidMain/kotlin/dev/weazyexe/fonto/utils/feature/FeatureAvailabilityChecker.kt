package dev.weazyexe.fonto.utils.feature

import android.os.Build

actual class FeatureAvailabilityChecker {

    actual fun isFeatureAvailable(feature: Feature): Boolean =
        when(feature) {
            Feature.DYNAMIC_COLOR -> Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
        }
}