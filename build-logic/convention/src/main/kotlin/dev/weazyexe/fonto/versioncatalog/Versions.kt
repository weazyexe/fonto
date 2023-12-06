package dev.weazyexe.fonto.versioncatalog

import dev.weazyexe.fonto.toInt
import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.VersionCatalog

val VersionCatalog.compileSdk
    get(): Int = findVersion("compileSdk").toInt()

val VersionCatalog.minSdk
    get(): Int = findVersion("minSdk").toInt()

val VersionCatalog.targetSdk
    get(): Int = findVersion("targetSdk").toInt()

val VersionCatalog.applicationId
    get(): String = findVersion("applicationId").get().toString()

val VersionCatalog.composeCompiler
    get(): String = findVersion("composeCompiler").get().toString()

val VersionCatalog.java
    get(): JavaVersion = JavaVersion.VERSION_17

