rootProject.name = "fonto"
include(":shared")
include(":android")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("multiplatform").version("1.8.10")
        kotlin("plugin.serialization").version("1.8.10")
        id("com.android.library").version("7.4.2")
        id("com.squareup.sqldelight").version("1.5.5")
        id("com.android.application") version "7.4.2"
        id("org.jetbrains.kotlin.android") version "1.7.0"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            library("kotlinx-coroutines-core", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            library("touchlab-kermit", "co.touchlab:kermit:1.2.2")
            library("kotlinx-serialization-json", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
            library("kotlinx-datetime", "org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            library("aakira-napier", "io.github.aakira:napier:2.6.1")
            library("ktor-client-core", "io.ktor:ktor-client-core:2.2.4")
            library("ktor-client-okhttp", "io.ktor:ktor-client-okhttp:2.2.4")
            library("ktor-client-ios", "io.ktor:ktor-client-ios:2.2.4")
            library("sqldelight-coroutines", "com.squareup.sqldelight:coroutines-extensions:1.5.5")
            library("sqldelight-driver-android", "com.squareup.sqldelight:android-driver:1.5.5")
            library("sqldelight-driver-native", "com.squareup.sqldelight:native-driver:1.5.5")
        }
    }
}
