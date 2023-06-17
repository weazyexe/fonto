package dev.weazyexe.fonto.common.db

import app.cash.sqldelight.db.SqlDriver
import dev.weazyexe.fonto.db.FontoDatabase

internal expect class DriverFactory {
    fun createDriver(): SqlDriver
}

internal fun createDatabase(driverFactory: DriverFactory): FontoDatabase {
    val driver = driverFactory.createDriver()
    return FontoDatabase(driver)
}
