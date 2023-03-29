plugins {
    alias(libs.plugins.kotlin.multiplatform) version libs.versions.kotlin apply false
    alias(libs.plugins.kotlin.serialization) version libs.versions.kotlin apply false
    alias(libs.plugins.android.library) version libs.versions.gradlePlugin apply false
    alias(libs.plugins.android.application) version libs.versions.gradlePlugin apply false
    alias(libs.plugins.sqldelight) version libs.versions.sqlDelight apply false
    alias(libs.plugins.kotlin.android) version libs.versions.kotlin apply false
}
