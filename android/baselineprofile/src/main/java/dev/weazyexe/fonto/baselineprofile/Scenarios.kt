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
    val listSelector = By.res("posts_list")
    val postItem = By.res("post_item")
    device.wait(Until.hasObject(postItem), 10_000L)
    val list = device.findObject(listSelector)
    list.fling(Direction.DOWN)
    list.fling(Direction.DOWN)
    list.fling(Direction.UP)
}

fun MacrobenchmarkScope.useDateRangePicker() {
    val searchSelector = By.res("search_button")
    val closeSearchSelector = By.res("close_search_button")
    val datesRangeSelector = By.res("filter_dates_range")
    val dateRangePickerSelector = By.res("date_range_picker")
    val closeDialogButtonSelector = By.res("close_dialog_button")

    device.wait(Until.hasObject(searchSelector), 10_000)
    val searchButton = device.findObject(searchSelector)
    searchButton.click()

    device.wait(Until.hasObject(datesRangeSelector), 10_000)
    val datesRange = device.findObject(datesRangeSelector)
    datesRange.click()
    device.wait(Until.hasObject(dateRangePickerSelector), 10_000)

    val dateRangePicker = device.findObject(dateRangePickerSelector)
    dateRangePicker.fling(Direction.DOWN, 10_000)
    dateRangePicker.fling(Direction.DOWN, 10_000)

    val closeDialogButton = device.findObject(closeDialogButtonSelector)
    closeDialogButton.click()

    device.wait(Until.hasObject(closeSearchSelector), 10_000)
    val closeSearchButton = device.findObject(closeSearchSelector)
    closeSearchButton.clickAndWait(Until.newWindow(), 10_000)
}