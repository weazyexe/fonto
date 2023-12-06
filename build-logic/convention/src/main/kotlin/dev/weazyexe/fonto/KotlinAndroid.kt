package dev.weazyexe.fonto

import com.android.build.api.dsl.CommonExtension
import dev.weazyexe.fonto.versioncatalog.compileSdk
import dev.weazyexe.fonto.versioncatalog.java
import dev.weazyexe.fonto.versioncatalog.minSdk
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = libs.compileSdk

        defaultConfig {
            minSdk = libs.minSdk
        }

        compileOptions {
            sourceCompatibility = libs.java
            targetCompatibility = libs.java
        }
    }

    configureKotlin()
}

/**
 * Configure base Kotlin options
 */
private fun Project.configureKotlin() {
    // Use withType to workaround https://youtrack.jetbrains.com/issue/KT-55947
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = libs.java.toString()
        }
    }
}
