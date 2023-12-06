import dev.weazyexe.fonto.ComposeDestinationsExtensions

plugins {
    alias(libs.plugins.fonto.android.library)
    alias(libs.plugins.fonto.android.library.compose)
    alias(libs.plugins.fonto.android.library.compose.destinations)
}

configure<ComposeDestinationsExtensions> {
    mode = ComposeDestinationsExtensions.Mode.NavGraphs
    moduleName = "core-ui"
}

android {
    namespace = libs.versions.applicationId.get() + ".core.ui"
}

dependencies {
    implementation(project(":shared"))

    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.datetime)

    implementation(libs.compose.material)

    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.webkit)

    implementation(libs.napier)

    implementation(libs.material)
}
