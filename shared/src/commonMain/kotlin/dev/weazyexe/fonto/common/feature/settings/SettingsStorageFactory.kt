package dev.weazyexe.fonto.common.feature.settings

expect class SettingsStorageFactory {

    fun create(): SettingsStorage
}

fun createSettingsStorage(factory: SettingsStorageFactory): SettingsStorage {
    return factory.create()
}