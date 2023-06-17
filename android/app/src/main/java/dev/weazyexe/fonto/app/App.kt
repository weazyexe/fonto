package dev.weazyexe.fonto.app

import android.app.Application
import dev.weazyexe.fonto.common.app.AppInitializer
import dev.weazyexe.fonto.di.appModule
import dev.weazyexe.fonto.di.dataModules
import dev.weazyexe.fonto.di.screenModules
import dev.weazyexe.fonto.ui.features.feed.di.feedModule
import dev.weazyexe.fonto.ui.features.settings.di.settingsModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    private val appScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val appInitializer by inject<AppInitializer>()

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())

        startKoin {
            androidContext(this@App)

            modules(dataModules())
            modules(screenModules())

            modules(
                appModule,
                feedModule,
                settingsModule
            )
        }

        appScope.launch {
            appInitializer.initialize()
        }
    }
}