package dev.weazyexe.navigation.provider

import androidx.activity.result.ActivityResultRegistry

class ActivityResultRegistryProvider(private val activityProvider: ActivityProvider) {

    fun get(): ActivityResultRegistry =
        activityProvider.get().activityResultRegistry
}
