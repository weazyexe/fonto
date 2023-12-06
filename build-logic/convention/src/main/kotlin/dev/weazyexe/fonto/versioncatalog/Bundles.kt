package dev.weazyexe.fonto.versioncatalog

import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider

val VersionCatalog.composeDestinationsBundle
    get(): Provider<ExternalModuleDependencyBundle> = findBundle("compose-destinations").get()
