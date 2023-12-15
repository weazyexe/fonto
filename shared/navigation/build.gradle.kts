plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget()

    sourceSets {
        /* Main source sets */
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(libs.kotlinx.coroutines)
                implementation(libs.napier)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.compose.destinations.core)
                implementation(libs.androidx.browser)
                implementation(libs.androidx.appcompat)
            }
        }

        /* Main hierarchy */
        androidMain.dependsOn(commonMain)

        /* Test source sets */
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidUnitTest by getting

        /* Test hierarchy */
        androidUnitTest.dependsOn(commonTest)
    }
}

android {
    namespace = "dev.weazyexe.navigation"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
