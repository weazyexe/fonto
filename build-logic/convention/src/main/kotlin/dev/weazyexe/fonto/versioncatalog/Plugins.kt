package dev.weazyexe.fonto.versioncatalog

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency

val VersionCatalog.androidApplication
    get(): Provider<PluginDependency> = findPlugin("android-application").get()

val VersionCatalog.androidLibrary
    get(): Provider<PluginDependency> = findPlugin("android-library").get()

val VersionCatalog.androidTest
    get(): Provider<PluginDependency> = findPlugin("android-test").get()

val VersionCatalog.kotlinAndroid
    get(): Provider<PluginDependency> = findPlugin("kotlin-android").get()

val VersionCatalog.kotlinKsp
    get(): Provider<PluginDependency> = findPlugin("kotlin-ksp").get()

val VersionCatalog.kotlinSerialization
    get(): Provider<PluginDependency> = findPlugin("kotlin-serialization").get()

val VersionCatalog.kotlinParcelize
    get(): Provider<PluginDependency> = findPlugin("kotlin-parcelize").get()
