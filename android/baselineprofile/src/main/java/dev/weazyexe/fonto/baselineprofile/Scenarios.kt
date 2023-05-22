package dev.weazyexe.fonto.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until

fun MacrobenchmarkScope.baselineScenario() {
    startActivityAndWait()
    useDateRangePicker()
    scrollFeed()
}

fun MacrobenchmarkScope.scrollFeed() {
    val listSelector = By.res("newsline_list")
    device.wait(Until.hasObject(listSelector), 10_000L)
    val list = device.findObject(listSelector)
    list.fling(Direction.DOWN)
    list.fling(Direction.DOWN)
    list.fling(Direction.UP)
}

fun MacrobenchmarkScope.useDateRangePicker() {
    val datesRangeSelector = By.res("filter_dates_range")
    val dateRangePickerSelector = By.res("date_range_picker")
    val closeDialogButtonSelector = By.res("close_dialog_button")

    device.wait(Until.hasObject(datesRangeSelector), 10_000)
    val datesRange = device.findObject(datesRangeSelector)
    datesRange.click()
    device.wait(Until.hasObject(dateRangePickerSelector), 10_000)

    val dateRangePicker = device.findObject(dateRangePickerSelector)
    dateRangePicker.fling(Direction.DOWN, 25_000)
    dateRangePicker.fling(Direction.DOWN, 25_000)

    val closeDialogButton = device.findObject(closeDialogButtonSelector)
    closeDialogButton.clickAndWait(Until.newWindow(), 10_000)
}