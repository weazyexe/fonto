rootProject.name = "fonto"

include(":shared")
include(":android:app")
include(":android:debug")
include(":android:core:ui")

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
