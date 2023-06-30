package dev.weazyexe.fonto.di

import android.content.Context
import dev.weazyexe.fonto.app.background.SyncPostsWorker
import dev.weazyexe.fonto.ui.AppViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val appModule = module {
    viewModel { AppViewModel(get()) }
    worker {
        SyncPostsWorker(
            syncPostsBackgroundTask = get(),
            context = androidApplication() as Context,
            workerParams = get()
        )
    }
}