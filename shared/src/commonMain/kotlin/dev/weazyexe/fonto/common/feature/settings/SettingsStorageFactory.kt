package dev.weazyexe.fonto.common.feature.settings

internal expect class SettingsStorageFactory {

    fun create(): SettingsStorage
}

internal fun createSettingsStorage(factory: SettingsStorageFactory): SettingsStorage {
    return factory.create()
}