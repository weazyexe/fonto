import dev.weazyexe.fonto.ComposeDestinationsExtensions

plugins {
    alias(libs.plugins.fonto.android.app)
    alias(libs.plugins.fonto.android.app.compose)
    alias(libs.plugins.fonto.android.app.compose.destinations)
}

configure<ComposeDestinationsExtensions> {
    mode = ComposeDestinationsExtensions.Mode.SingleModule
    moduleName = "app"
}

android {
    namespace = libs.versions.applicationId.get()

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            resValue("string", "app_name", "Fonto Debug")
        }

        create("qa") {
            applicationIdSuffix = ".qa"
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            resValue("string", "app_name", "Fonto QA")
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        create("benchmark") {
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
            proguardFiles("benchmark-rules.pro")
            resValue("string", "app_name", "Fonto Benchmark")
        }
    }
}

dependencies {

    implementation(project(":shared")) {
        exclude("pull-parser", "pull-parser")
    }
    implementation(project(":android:core:ui"))
    implementation(project(":android:debug")) // FIXME use debugImplementation

    implementation(project(":android:feature:feed"))

    implementation(libs.material)

    implementation(libs.kotlinx.serialization)

    implementation(libs.napier)

    implementation(libs.accompanist.webview)

    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.browser)

    implementation(libs.coil)
    implementation(libs.kotlinx.datetime)

    implementation(libs.koin.android)
    implementation(libs.koin.android.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.espresso)
}
