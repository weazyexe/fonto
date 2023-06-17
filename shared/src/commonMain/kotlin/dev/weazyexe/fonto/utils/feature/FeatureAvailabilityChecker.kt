package dev.weazyexe.fonto.utils.feature

internal expect class FeatureAvailabilityChecker {
    fun isFeatureAvailable(feature: Feature): Boolean
}