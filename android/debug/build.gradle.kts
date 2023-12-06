import dev.weazyexe.fonto.ComposeDestinationsExtensions

plugins {
    alias(libs.plugins.fonto.android.library)
    alias(libs.plugins.fonto.android.library.compose)
    alias(libs.plugins.fonto.android.library.compose.destinations)
}

configure<ComposeDestinationsExtensions> {
    mode = ComposeDestinationsExtensions.Mode.NavGraphs
    moduleName = "debug"
}

android {
    namespace = libs.versions.applicationId.get() + ".debug"
}

dependencies {

    implementation(project(":android:core:ui"))
    implementation(project(":shared"))

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.test.manifest)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)

    implementation(libs.koin.android)
    implementation(libs.koin.android.compose)

    implementation(libs.compose.destinations.core)
    implementation(libs.compose.destinations.animations)
    ksp(libs.compose.destinations.ksp)
}
