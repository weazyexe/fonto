rootProject.name = "fonto"

pluginManagement {
    includeBuild("build-logic")

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(":shared")
include(":android:app")
include(":android:debug")
include(":android:core:ui")
include(":android:feature:feed")
include(":android:baselineprofile")
