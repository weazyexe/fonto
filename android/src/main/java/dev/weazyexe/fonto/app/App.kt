package dev.weazyexe.fonto.app

import android.app.Application
import dev.weazyexe.fonto.common.di.appModules
import dev.weazyexe.fonto.ui.features.feed.di.feedModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())

        startKoin {
            androidContext(this@App)
            modules(appModules())
            modules(feedModule)
        }
    }
}