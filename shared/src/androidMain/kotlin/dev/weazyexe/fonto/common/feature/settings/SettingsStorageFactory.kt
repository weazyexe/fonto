package dev.weazyexe.fonto.common.feature.settings

import android.content.Context

internal actual class SettingsStorageFactory(
    private val context: Context
) {

    actual fun create(): SettingsStorage = AndroidSettingsStorage(context)
}