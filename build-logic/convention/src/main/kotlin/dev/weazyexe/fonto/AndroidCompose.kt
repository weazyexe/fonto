package dev.weazyexe.fonto

import com.android.build.api.dsl.CommonExtension
import dev.weazyexe.fonto.versioncatalog.composeBom
import dev.weazyexe.fonto.versioncatalog.composeCompiler
import dev.weazyexe.fonto.versioncatalog.composeMaterial3
import dev.weazyexe.fonto.versioncatalog.composeTestManifest
import dev.weazyexe.fonto.versioncatalog.composeUi
import dev.weazyexe.fonto.versioncatalog.composeUiTooling
import dev.weazyexe.fonto.versioncatalog.composeUiToolingPreview
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.composeCompiler
        }

        dependencies {
            val bom = libs.composeBom
            add("implementation", platform(bom))
            add("implementation", libs.composeMaterial3)
            add("implementation", libs.composeUi)
            add("implementation", libs.composeUiToolingPreview)
            add("debugImplementation", libs.composeUiTooling)
            add("debugImplementation", libs.composeTestManifest)
            add("androidTestImplementation", platform(bom))
        }
    }
}
