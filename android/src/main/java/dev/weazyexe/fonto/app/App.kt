package dev.weazyexe.fonto.app

import android.app.Application
import dev.weazyexe.fonto.common.di.appModules
import dev.weazyexe.fonto.ui.screens.feed.managefeed.di.manageFeedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                *appModules().toTypedArray(),
                manageFeedModule
            )
        }
    }
}