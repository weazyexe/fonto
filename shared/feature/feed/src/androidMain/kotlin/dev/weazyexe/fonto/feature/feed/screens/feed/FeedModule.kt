package dev.weazyexe.fonto.feature.feed.screens.feed

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val FeedPlatformModule = module {
    factory { FeedUiMessages(context = androidContext()) }
}
