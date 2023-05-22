package dev.weazyexe.fonto.common.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.weazyexe.fonto.db.FontoDatabase

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(FontoDatabase.Schema, context, "fonto1.db")
    }
}