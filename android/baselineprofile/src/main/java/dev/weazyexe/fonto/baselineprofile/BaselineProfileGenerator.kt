package dev.weazyexe.fonto.baselineprofile

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalBaselineProfilesApi::class)
class BaselineProfileGenerator {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun startup() =
        baselineProfileRule.collectBaselineProfile(
            packageName = "dev.weazyexe.fonto",
            profileBlock = {
                startupAndScrollFeed()
            }
        )
}

fun MacrobenchmarkScope.startupAndScrollFeed() {
    startActivityAndWait()
    val listSelector = By.res("newsline_list")
    device.wait(Until.hasObject(listSelector), 10_000L)
    val list = device.findObject(listSelector)
    list.fling(Direction.DOWN)
    list.fling(Direction.DOWN)
    list.fling(Direction.UP)
}