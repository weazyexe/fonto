package dev.weazyexe.fonto.baselineprofile

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalBaselineProfilesApi::class)
class BaselineProfiles {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun startupAndScrollFeed() =
        baselineProfileRule.collectBaselineProfile(
            packageName = "dev.weazyexe.fonto",
            profileBlock = {
                startupAndScrollFeed()
            }
        )

    @Test
    fun startupAndUseDateRangePicker() =
        baselineProfileRule.collectBaselineProfile(
            packageName = "dev.weazyexe.fonto",
            profileBlock = {
                startupAndUseDateRangePicker()
            }
        )
}