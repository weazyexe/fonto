package dev.weazyexe.fonto.versioncatalog

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider

val VersionCatalog.composeBom
    get(): Provider<MinimalExternalModuleDependency> = findLibrary("compose-bom").get()

val VersionCatalog.composeMaterial3
    get(): Provider<MinimalExternalModuleDependency> = findLibrary("compose-material3").get()

val VersionCatalog.composeUi
    get(): Provider<MinimalExternalModuleDependency> = findLibrary("compose-ui").get()

val VersionCatalog.composeUiTooling
    get(): Provider<MinimalExternalModuleDependency> = findLibrary("compose-ui-tooling").get()

val VersionCatalog.composeUiToolingPreview
    get(): Provider<MinimalExternalModuleDependency> = findLibrary("compose-ui-tooling-preview").get()

val VersionCatalog.composeTestManifest
    get(): Provider<MinimalExternalModuleDependency> = findLibrary("compose-test-manifest").get()

val VersionCatalog.composeDestinationsKsp
    get(): Provider<MinimalExternalModuleDependency> = findLibrary("compose-destinations-ksp").get()
