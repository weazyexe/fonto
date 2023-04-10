package dev.weazyexe.fonto.common.db

import app.cash.sqldelight.db.SqlDriver
import dev.weazyexe.fonto.db.FontoDatabase

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): FontoDatabase {
    val driver = driverFactory.createDriver()
    return FontoDatabase(driver)
}
