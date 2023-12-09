@file:JvmName("AndroidFeedPlatformModule")
package dev.weazyexe.fonto.feature.feed.screens.feed.di

import dev.weazyexe.fonto.feature.feed.screens.feed.FeedUiMessages
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val FeedPlatformModule = module {
    factory { FeedUiMessages(context = androidContext()) }
}
