plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.sqldelight)
}

kotlin {
    android()
    /*iosX64()
    iosArm64()
    iosSimulatorArm64()*/

    sourceSets {
        /* Main source sets */
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines)
                implementation(libs.kermit)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.serialization)
                implementation(libs.napier)
                implementation(libs.ktor)
                implementation(libs.ktor.logging)
                implementation(libs.koin.core)
                implementation(libs.sqldelight.coroutines)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.okhttp)
                implementation(libs.sqldelight.android)
                implementation(libs.rss.parser)
                implementation(libs.koin.android)
            }
        }
        /*val iosMain by creating {
            dependencies {
                implementation(libs.ktor.client.ios)
                implementation(libs.sqldelight.driver.native)
            }
        }
        val iosX64Main by getting 
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting*/

        /* Main hierarchy */
        androidMain.dependsOn(commonMain)
        /*iosMain.dependsOn(commonMain)
        iosX64Main.dependsOn(iosMain)
        iosArm64Main.dependsOn(iosMain)
        iosSimulatorArm64Main.dependsOn(iosMain)*/

        /* Test source sets */
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidUnitTest by getting
        /*val iosTest by creating
        val iosX64Test by getting 
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting*/

        /* Test hierarchy */
        androidUnitTest.dependsOn(commonTest)
        /*iosTest.dependsOn(commonTest)
        iosX64Test.dependsOn(iosTest)
        iosArm64Test.dependsOn(iosTest)
        iosSimulatorArm64Test.dependsOn(iosTest)*/
    }
}

android {
    namespace = "${libs.versions.applicationId.get()}.common"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    databases {
        create("FontoDatabase") {
            packageName.set("${libs.versions.applicationId.get()}.db")
//            sourceFolders.set(listOf("src/commonMain/sqldelight"))
        }
    }
}
