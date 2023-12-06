import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "dev.weazyexe.fonto.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        create("fonto_android_app") {
            id = "dev.weazyexe.fonto.android.app"
            implementationClass = "AndroidAppConvention"
        }
        create("fonto_android_app_compose") {
            id = "dev.weazyexe.fonto.android.app.compose"
            implementationClass = "AndroidAppComposeConvention"
        }
        create("fonto_android_app_compose_destinations_app") {
            id = "dev.weazyexe.fonto.android.app.compose.destinations"
            implementationClass = "ComposeDestinationsAppConvention"
        }

        create("fonto_android_library") {
            id = "dev.weazyexe.fonto.android.library"
            implementationClass = "AndroidLibraryConvention"
        }
        create("fonto_android_library_compose") {
            id = "dev.weazyexe.fonto.android.library.compose"
            implementationClass = "AndroidLibraryComposeConvention"
        }
        create("fonto_android_library_compose_destinations_app") {
            id = "dev.weazyexe.fonto.android.library.compose.destinations"
            implementationClass = "ComposeDestinationsLibraryConvention"
        }

        create("fonto_android_test") {
            id = "dev.weazyexe.fonto.android.test"
            implementationClass = "AndroidTestConvention"
        }
    }
}
