package dev.weazyexe.navigation.provider

import androidx.appcompat.app.AppCompatActivity

class ActivityProvider {

    private var currentActivity: AppCompatActivity? = null

    fun get() = currentActivity ?: error("No current activity")

    fun set(activity: AppCompatActivity?) {
        this.currentActivity = activity
    }
}
