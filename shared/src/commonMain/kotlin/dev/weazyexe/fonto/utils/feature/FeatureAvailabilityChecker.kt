package dev.weazyexe.fonto.utils.feature

expect class FeatureAvailabilityChecker {
    fun isFeatureAvailable(feature: Feature): Boolean
}