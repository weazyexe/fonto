package dev.weazyexe.fonto.app

import android.app.Application
import dev.weazyexe.fonto.common.di.appModules
import dev.weazyexe.fonto.debug.di.debugModule
import dev.weazyexe.fonto.di.appModule
import dev.weazyexe.fonto.ui.features.feed.di.feedModule
import dev.weazyexe.fonto.ui.features.settings.di.settingsModule
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
            modules(
                appModule,
                feedModule,
                settingsModule,
                debugModule
            )
        }
    }
}