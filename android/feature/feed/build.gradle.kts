import dev.weazyexe.fonto.ComposeDestinationsExtensions

plugins {
    alias(libs.plugins.fonto.android.library)
    alias(libs.plugins.fonto.android.library.compose)
    alias(libs.plugins.fonto.android.library.compose.destinations)
}

configure<ComposeDestinationsExtensions> {
    mode = ComposeDestinationsExtensions.Mode.Destinations
    moduleName = "Feed"
}

android {
    namespace = libs.versions.applicationId.get() + ".feature.feed"
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":shared:elm"))
    implementation(project(":shared:navigation"))

    implementation(project(":shared:feature:feed"))

    implementation(project(":android:core:ui"))

    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.datetime)

    implementation(libs.napier)

    implementation(libs.coil)

    implementation(libs.koin.android)
    implementation(libs.koin.android.compose)

    implementation(libs.androidx.browser)
}
