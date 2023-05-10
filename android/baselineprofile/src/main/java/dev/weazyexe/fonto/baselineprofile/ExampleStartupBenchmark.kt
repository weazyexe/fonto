package dev.weazyexe.fonto.baselineprofile

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalBaselineProfilesApi::class)
class BaselineProfileGenerator {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    /**
     *
     *  @packageName Same as your applicationId (:app Gradle file)
     *
     **/
    @Test
    fun startup() =
        baselineProfileRule.collectBaselineProfile(
            packageName = "dev.weazyexe.fonto",
            profileBlock = {
                startActivityAndWait()
            }
        )
}