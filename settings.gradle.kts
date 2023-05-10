rootProject.name = "fonto"

include(":shared")
include(":android:app")
include(":android:debug")
include(":android:core:ui")
include(":android:baselineprofile")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
