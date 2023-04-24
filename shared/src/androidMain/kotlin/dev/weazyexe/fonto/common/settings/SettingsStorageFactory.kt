package dev.weazyexe.fonto.common.settings

import android.content.Context

actual class SettingsStorageFactory(
    private val context: Context
) {

    actual fun create(): SettingsStorage = AndroidSettingsStorage(context)
}