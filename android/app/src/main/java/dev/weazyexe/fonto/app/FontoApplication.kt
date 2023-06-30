package dev.weazyexe.fonto.app

import android.app.Application
import dev.weazyexe.fonto.BuildConfig
import dev.weazyexe.fonto.app.background.SyncPostsWorker
import dev.weazyexe.fonto.common.app.initializer.AppInitializer
import dev.weazyexe.fonto.di.appModule
import dev.weazyexe.fonto.di.dataModules
import dev.weazyexe.fonto.di.screenModules
import dev.weazyexe.fonto.ui.features.feed.di.feedModule
import dev.weazyexe.fonto.ui.features.settings.di.settingsModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class FontoApplication : Application() {

    private val appInitializer by inject<AppInitializer>()
    private val appScope by inject<CoroutineScope>()
    private val syncPostsWorker by inject<SyncPostsWorker>()

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())

        startKoin {
            androidContext(this@FontoApplication)
            workManagerFactory()

            modules(dataModules())
            modules(screenModules())

            modules(
                appModule,
                feedModule,
                settingsModule
            )
        }

        appScope.launch {
            appInitializer.initialize(
                arguments = AppInitializer.Args(
                    areMockFeedsEnabled = BuildConfig.BUILD_TYPE == "benchmark"
                )
            )


        }
    }
}